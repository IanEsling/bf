/**
 */
public class EndBracketNotFoundException extends ParserException
{
    public EndBracketNotFoundException()
    {
        super("end bracket not found when parsing program");
    }
}
