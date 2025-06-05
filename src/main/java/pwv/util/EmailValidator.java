package pwv.util;

public class EmailValidator {
    // input classes
    static final int LETTER     = 0;
    static final int DIGIT      = 1;
    static final int DOT        = 2;
    static final int AT         = 3;
    static final int HYPHEN     = 4;
    static final int PLUS       = 5;
    static final int UNDERSCORE = 6;
    static final int OTHER      = 7;

    // states
    // 0 = START (nothing seen yet)
    // 1 = LOCAL (before @)
    // 2 = AT_SEEN (just saw @)
    // 3 = DOMAIN (after @, before dot)
    // 4 = DOT_SEEN (just saw the dot before TLD)
    // 5 = TLD1 (1 char of TLD)
    // 6 = TLD2 (2 chars of TLD)
    // 7 = TLD3 (3 chars of TLD)
    // 8 = TLD4 (4 chars of TLD)
    // -1 = INVALID

    // transition table: [currentState][inputClass] -> nextState
    // columns: LETTER, DIGIT, DOT, AT, HYPHEN, PLUS, UNDERSCORE, OTHER
    private final int[][] transition = {
      /* 0 START */    {   1,     1,    -1,    -1,      -1,     -1,        -1,      -1 },
      /* 1 LOCAL */    {   1,     1,     1,     2,       1,      1,         1,      -1 },
      /* 2 AT_SEEN */  {   3,     3,    -1,    -1,       3,      3,         3,      -1 },
      /* 3 DOMAIN */   {   3,     3,     4,    -1,       3,      3,         3,      -1 },
      /* 4 DOT_SEEN */ {   5,     5,    -1,    -1,       5,      5,         5,      -1 },
      /* 5 TLD1 */     {   6,     6,    -1,    -1,       6,      6,         6,      -1 },
      /* 6 TLD2 */     {   7,     7,    -1,    -1,       7,      7,         7,      -1 },
      /* 7 TLD3 */     {   8,     8,    -1,    -1,       8,      8,         8,      -1 },
      /* 8 TLD4 */     {  -1,    -1,    -1,    -1,      -1,     -1,        -1,      -1 }
    };

    private int classify(char c) {
        if (Character.isLetter(c))       return LETTER;
        if (Character.isDigit(c))        return DIGIT;
        if (c == '.')                    return DOT;
        if (c == '@')                    return AT;
        if (c == '-')                    return HYPHEN;
        if (c == '+')                    return PLUS;
        if (c == '_')                    return UNDERSCORE;
        return OTHER;
    }

    public boolean isValid(String email) {
        int state = 0;
        for (char c : email.toCharArray()) {
            int cls = classify(c);
            // if state is already invalid, break
            if (state < 0) break;
            state = transition[state][cls];
            if (state < 0) break;
        }
        // valid if we ended in state 6, 7 or 8 (2–4 chars of TLD)
        return (state == 6 || state == 7 || state == 8);
    }

    
    /*public static void main(String[] args) {
        String[] tests = {
            "till@reichardt.io",
            "tisdfsfd.sdf@example.ddio",
            "max@gymnasium-norf.de",
            "max_mustermann@google.de",
            "invalid@toolongdddddd.io",
            "bad@format.",
            "noatsign.com"
        };
        EmailValidator v = new EmailValidator();
        for (String e : tests) {
            System.out.printf("%-30s -> %s%n", e, v.isValid(e) ? "valid" : "invalid");
        }
    }
    */
    
}
