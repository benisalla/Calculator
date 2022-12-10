package com.exemple.calculator;

public class Math_Exp_Evaluator {
    public static String ErrorMessage="";
    private static final double ASINH_COEF[] = { -.12820039911738186343372127359268e+0,
            -.58811761189951767565211757138362e-1, .47274654322124815640725249756029e-2,
            -.49383631626536172101360174790273e-3, .58506207058557412287494835259321e-4,
            -.74669983289313681354755069217188e-5, .10011693583558199265966192015812e-5,
            -.13903543858708333608616472258886e-6, .19823169483172793547317360237148e-7,
            -.28847468417848843612747272800317e-8, .42672965467159937953457514995907e-9,
            -.63976084654366357868752632309681e-10, .96991686089064704147878293131179e-11,
            -.14844276972043770830246658365696e-11, .22903737939027447988040184378983e-12,
            -.35588395132732645159978942651310e-13, .55639694080056789953374539088554e-14,
            -.87462509599624678045666593520162e-15, .13815248844526692155868802298129e-15,
            -.21916688282900363984955142264149e-16, .34904658524827565638313923706880e-17 };
    private static final double ATANH_COEF[] = { .9439510239319549230842892218633e-1,
            .4919843705578615947200034576668e-1, .2102593522455432763479327331752e-2,
            .1073554449776116584640731045276e-3, .5978267249293031478642787517872e-5,
            .3505062030889134845966834886200e-6, .2126374343765340350896219314431e-7,
            .1321694535715527192129801723055e-8, .8365875501178070364623604052959e-10,
            .5370503749311002163881434587772e-11, .3486659470157107922971245784290e-12,
            .2284549509603433015524024119722e-13, .1508407105944793044874229067558e-14,
            .1002418816804109126136995722837e-15, .6698674738165069539715526882986e-17,
            .4497954546494931083083327624533e-18 };
    //--------------------------------------------------------------------------------
    static public double atanh(double x) {
        double y = Math.abs(x);
        double ans;

        if (Double.isNaN(x)) {
            ans = Double.NaN;
        } else if (y < 1.82501e-08) {
            ans = x;
        } else if (y <= 0.5) {
            ans = x * (1.0 + csevl(8.0 * x * x - 1.0, ATANH_COEF));
        } else if (y < 1.0) {
            ans = 0.5 * Math.log((1.0 + x) / (1.0 - x));
        } else if (y == 1.0) {
            ans = x * Double.POSITIVE_INFINITY;
        } else {
            ans = Double.NaN;
        }
        return ans;
    }
    //--------------------------------------------------------------------------------
    static public double asinh(double x) {
        double ans;
        double y = Math.abs(x);

        if (Double.isNaN(x)) {
            ans = Double.NaN;
        } else if (y <= 1.05367e-08) {
            ans = x;
        } else if (y <= 1.0) {
            ans = x * (1.0 + csevl(2.0 * x * x - 1.0, ASINH_COEF));
        } else if (y < 94906265.62) {
            ans = Math.log(y + Math.sqrt(y * y + 1.0));
        } else {
            ans = 0.69314718055994530941723212145818 + Math.log(y);
        }
        if (x < 0.0)
            ans = -ans;
        return ans;
    }
    //--------------------------------------------------------------------------------
    static double csevl(double x, double coef[]) {
        double b0, b1, b2, twox;
        int i;
        b1 = 0.0;
        b0 = 0.0;
        b2 = 0.0;
        twox = 2.0 * x;
        for (i = coef.length - 1; i >= 0; i--) {
            b2 = b1;
            b1 = b0;
            b0 = twox * b1 - b2 + coef[i];
        }
        return 0.5 * (b0 - b2);
    }
    //--------------------------------------------------------------------------------
    public static double acosh(double x) {
        double ans;

        if (Double.isNaN(x) || (x < 1)) {
            ans = Double.NaN;
        }

        else if (x < 94906265.62) {
            ans = safeLog(x + Math.sqrt(x * x - 1.0D));
        } else {
            ans = 0.69314718055994530941723212145818D + safeLog(x);
        }

        return ans;
    }
    //--------------------------------------------------------------------------------
    public static double safeLog(double x) {
        if (x == 0.0D) {
            return 0.0D;
        } else {
            return Math.log(x);
        }
    }
    //--------------------------------------------------------------------------------
    public static double CalculateFromExpression(final String exp) {
        return new Object() {
            int current = -1, next;
            //--------------------------------------------------------
            void getNextChar() {
                next = (++current < exp.length()) ? exp.charAt(current) : -1;
            }
            //--------------------------------------------------------
            boolean consume(int nextCharToConcume) {
                while (next == ' ') getNextChar();
                if (next == nextCharToConcume) {
                    getNextChar();
                    return true;
                }
                return false;
            }
            //--------------------------------------------------------
            double parse() {
                getNextChar();
                double x = parseExpression();
                if (current < exp.length()) ErrorMessage = "Unexpected: " + (char)next;
                return x;
            }
            //--------------------------------------------------------
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (consume('+')) x += parseTerm();
                    else if (consume('-')) x -= parseTerm();
                    else return x;
                }
            }
            //--------------------------------------------------------
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (consume('*')) x *= parseFactor();
                    else if (consume('/')) x /= parseFactor();
                    else if (consume('%')) x %= parseFactor();
                    else return x;
                }
            }
            //--------------------------------------------------------
            double parseFactor() {
                if (consume('+')) return +parseFactor();
                if (consume('-')) return -parseFactor();

                double x;
                int startcurrent = this.current;
                if (consume('(')) {
                    x = parseExpression();
                    if (!consume(')')) ErrorMessage = "Missing ')'";
                } else if ((next >= '0' && next <= '9') || next == '.') {
                    while ((next >= '0' && next <= '9') || next == '.') getNextChar();
                    x = Double.parseDouble(exp.substring(startcurrent, this.current));
                } else if (next >= 'a' && next <= 'z') {
                    while (next >= 'a' && next <= 'z') getNextChar();
                    String func = exp.substring(startcurrent, this.current);
                    if (consume('(')) {
                        x = parseExpression();
                        if (!consume(')')) ErrorMessage = "Missing ')' after argument to " + func;
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("asin")) x = Math.asin(Math.toRadians(x));
                    else if (func.equals("acos")) x = Math.acos(Math.toRadians(x));
                    else if (func.equals("atan")) x = Math.atan(Math.toRadians(x));
                    else if (func.equals("cosh")) x = Math.cosh(x);
                    else if (func.equals("sinh")) x = Math.sinh(x);
                    else if (func.equals("tanh")) x = Math.tanh(x);
                    else if (func.equals("acosh")) x = acosh(x);
                    else if (func.equals("asinh")) x = asinh(x);
                    else if (func.equals("atanh")) x = atanh(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("abs")) x = Math.abs(x);
                    else if (func.equals("exp")) x = Math.exp(x);
                    ErrorMessage = "Unknown function: " + func;
                } else {
                    ErrorMessage = "Unexpected: " + (char)next;
                    return 0;
                }
                if (consume('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }
}
