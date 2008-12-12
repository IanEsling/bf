/**
 */
public class StartBracketNotFoundException extends ParserException
{
    public StartBracketNotFoundException()
    {
        super("start bracket not found when parsing program");
    }
}