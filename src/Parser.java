/**
 */
public class Parser
{
    BfInterpreter interpreter;
    CharacterParser standardParser = new StandardParser();
    CharacterParser openBracketParser = new OpenBracketParser();
    CharacterParser closedBracketParser = new ClosedBracketParser();
    ByteOutputFormatter outputter = new ByteOutputFormatter();

    Parser(BfInterpreter interpreter)
    {
        this.interpreter = interpreter;
    }

    String formattedOutput()
    {
        return outputter.getOutput();
    }

    void parse(String program) throws ParserException
    {
        reset();

        for (int i = 0; i < program.length();)
        {
            i = getParser(program.charAt(i)).parseChar(program, i);
        }
    }

    private void reset()
    {
        interpreter.reset();
        outputter.reset();
    }

    CharacterParser getParser(char charToParse)
    {
        switch (charToParse)
        {
        case '[':
            return openBracketParser;
        case ']':
            return closedBracketParser;
        default:
            return standardParser;
        }
    }

    interface CharacterParser
    {
        int parseChar(String program, int charIndexToParse) throws ParserException;
    }

    class ClosedBracketParser implements CharacterParser
    {
        public int parseChar(String program, int charIndexToParse)
                throws StartBracketNotFoundException
        {
            if (interpreter.getByte() == 0)
            {
                return ++charIndexToParse;
            }

            return indexOfCorrespondingStartBracket(program, charIndexToParse);
        }

        private int indexOfCorrespondingStartBracket(String program, int charIndexToParse)
                throws StartBracketNotFoundException
        {
            int bracketTotal = 1;
            for (int i = --charIndexToParse; i > 0; i--)
            {
                switch (program.charAt(i))
                {
                case ']':
                    bracketTotal++;
                    break;
                case '[':
                    bracketTotal--;
                    break;
                }

                if (bracketTotal == 0) return i;
            }
            throw new StartBracketNotFoundException();
        }
    }

    class OpenBracketParser implements CharacterParser
    {
        public int parseChar(String program, int charIndexToParse) throws EndBracketNotFoundException
        {
            if (interpreter.getByte() != 0)
            {
                return ++charIndexToParse;
            }

            return indexOfCorrespondingEndBracket(program, charIndexToParse);
        }

        private int indexOfCorrespondingEndBracket(String program, int charIndexToParse) throws EndBracketNotFoundException
        {
            int bracketTotal = 1;
            for (int i = ++charIndexToParse; i < program.length(); i++)
            {
                switch (program.charAt(i))
                {
                case ']':
                    bracketTotal--;
                    break;
                case '[':
                    bracketTotal++;
                    break;
                }

                if (bracketTotal == 0) return i;
            }
            throw new EndBracketNotFoundException();
        }
    }

    class StandardParser implements CharacterParser
    {
        public int parseChar(String program, int charIndexToParse)
        {
            switch (program.charAt(charIndexToParse))
            {
            case '+':
                interpreter.incrementByte();
                break;
            case '-':
                interpreter.decrementByte();
                break;
            case '>':
                interpreter.incrementPointer();
                break;
            case '<':
                interpreter.decrementPointer();
                break;
            case '.':
                outputter.addByte(interpreter.getByte());
                break;
            }

            return ++charIndexToParse;
        }
    }
}
