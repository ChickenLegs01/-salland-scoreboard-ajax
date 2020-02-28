package nl.salland.scoreboard;
import java.util.ArrayList;
import java.util.List;

/*
 * Blood sweat and tears to work this out.
 */
public class ScoreBoardUtils {
	
	/*
	       digit3 | digit2 | digit1  = total + factor 
	factor = 0
	0 =         0 |      0 |      0  = 0  
	10 =        0 |   16+1 |      0  = 17
	12 =        0 |   16+1 |      2  = 19
	23 =        0 |   16+2 |      3  = 21
	58 =        0 |   16+5 |      8  = 29
	100 =    16+1 |   16+0 |      0  = 33
	123 =    16+1 |   16+2 |      3  = 38
	
	*/

	public static int createControlValueForDigits(int number) {
		if(number<10) return number;

		List<Integer> digits = new ArrayList<Integer>();
		while (number > 0) {
			digits.add(number%10);
			number/=10;
		}
		
		int total=0;

		// am here only if we have num>9 that means more than 1 digit.
		// the splitting splits the digits into reverse order so 
		// the 1st one is always the 0-9
		// the 2nd is the 10's digit.
		// the 3rd is the 100's digit.
		// and so on.
		for(Integer aInt: digits) {
			total = total + 16 + aInt;
		}
		return total-16;
	}
	
	public static String createControlValueForLineInHex(int left, int middle, int right, int factor) {
		int code = createControlValueForLine(left, middle, right, factor);

		String val = Integer.toHexString(code).toUpperCase();
		if(val.length()==1) {
			return "0" + val;
		} 
		
		return val;
 	}
	
	public static int createControlValueForLine(int left, int middle, int right, int factor) {
		int total=0;
		
		total = total + createControlValueForDigits(left);
		total = total + createControlValueForDigits(middle);
		total = total + createControlValueForDigits(right);
		total = total + factor;
		
//		System.out.println("hex=" + Integer.toHexString(total));
		
		return total;
	}
	
	static int FACTOR_LINE1 = 8;
	static int FACTOR_LINE2 = 9;
	static int FACTOR_LINE3 = 10;
	static int FACTOR_LINE4 = 11;
	static int FACTOR_LINE5 = 12;
	
	public static String BEGIN_STRING = "{?53|>U0001|+? }";
	
	static String PREFIX_LINE1 = "|>U0001|+L01|+P";
	static String PREFIX_LINE2 = "|>U0002|+L01|+P";
	static String PREFIX_LINE3 = "|>U0003|+L01|+P";
	static String PREFIX_LINE4 = "|>U0004|+L01|+P";
	static String PREFIX_LINE5 = "|>U0005|+L01|+P";
	
	static String FORMAT_LINE1 = "%2d %3d %3d";
	static String FORMAT_LINE2 = "%3d %d  %3d";
	static String FORMAT_LINE3 = "%2d %3d %3d";
	static String FORMAT_LINE4 = "%2d %3d %3d";
	static String FORMAT_LINE5 = "%2d %3d %3d";
	
	// zero line  {?08|>U0001|+L01|+P 0   0   0}
	public static String createLine1(int bat1, int total, int bat2) {
		String  controlcode = createControlValueForLineInHex(bat1, total, bat2, FACTOR_LINE1);
		return "{?" + controlcode + PREFIX_LINE1 + String.format(FORMAT_LINE1, bat1, total, bat2)+ "}";
	}

	public static String createLine2(int runs1, int wickets, int runs2) {
		String  controlcode = createControlValueForLineInHex(runs1, wickets, runs2, FACTOR_LINE2);
		return "{?" + controlcode + PREFIX_LINE2 + String.format(FORMAT_LINE2, runs1, wickets, runs2)+ "}";
	}

	public static String createLine3(int bowler1, int lastWicket, int lastman) {
		String  controlcode = createControlValueForLineInHex(bowler1, lastWicket, lastman, FACTOR_LINE3);
		return "{?" + controlcode + PREFIX_LINE3 + String.format(FORMAT_LINE3, bowler1, lastWicket, lastman)+ "}";
	}

	public static String createLine4(int bowler2, int prevInn, int RR) {
		String  controlcode = createControlValueForLineInHex(bowler2, prevInn, RR, FACTOR_LINE4);
		return "{?" + controlcode + PREFIX_LINE4 + String.format(FORMAT_LINE4, bowler2, prevInn, RR)+ "}";
	}

	public static String createLine5(int oversBowled, int DL, int oversRemain) {
		String  controlcode = createControlValueForLineInHex(oversBowled, DL, oversRemain, FACTOR_LINE5);
		return "{?" + controlcode + PREFIX_LINE5 + String.format(FORMAT_LINE5, oversBowled, DL, oversRemain)+ "}";
	}

	public static String createLineNoControl(String text, String prefix) {
		String tmp=text;
		if(text==null || text.length()==0) {
			tmp="";
		} else if(text.length()>10) {
			tmp = text.substring(0, 9);
		} else {
			tmp = text;
		}
		
		return "{?-" + prefix + tmp + "}";
	}
	
	public static String createLine5NoControl(String text) {
		return createLineNoControl(text, PREFIX_LINE5);
	}
	
	public static String createLine4NoControl(String text) {
		return createLineNoControl(text, PREFIX_LINE4);
	}

	public static String createLine3NoControl(String text) {
		return createLineNoControl(text, PREFIX_LINE3);
	}

	public static String createLine2NoControl(String text) {
		return createLineNoControl(text, PREFIX_LINE2);
	}

	public static String createLine1NoControl(String text) {
		return createLineNoControl(text, PREFIX_LINE1);
	}

	
	public static void main(String[] str) {
		int num = 0;

		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=9;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));
		
		num=10;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=12;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=23;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=58;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=99;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=100;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=101;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=110;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		num=123;
		System.out.println(num+ ", code=" + createControlValueForDigits(num));

		
		createControlValueForLine(30, 30, 20, 8);
		
		System.out.println("line1 0, 0, 0 = \t\t[" + createLine1(0, 0, 0) + "]");
		System.out.println("line1 1, 0, 1 = \t\t[" + createLine1(1, 0, 1) + "]");
		System.out.println("line1 1, 1, 1 = \t\t[" + createLine1(1, 1, 1) + "]");
		System.out.println("line1 10, 1, 1 = \t\t[" + createLine1(10, 1, 1) + "]");
		System.out.println("line1 1, 10, 1 = \t\t[" + createLine1(1, 10, 1) + "]");
		System.out.println("line1 10, 10, 1 = \t\t[" + createLine1(10, 10, 1) + "]");
		System.out.println("line1 10, 10, 10 = \t\t[" + createLine1(10, 10, 10) + "]");
		System.out.println("line1 10, 33, 10 = \t\t[" + createLine1(10, 33, 10) + "]");
		System.out.println("line1 1, 100, 10 = \t\t[" + createLine1(1, 100, 10) + "]");
		
		System.out.println("-----------------------");
		System.out.println("line1 0, 0, 0 = \t\t[" + createLine1(0, 0, 0) + "]");
		System.out.println("line2 0, 0, 0 = \t\t[" + createLine2(0, 0, 0) + "]");
		System.out.println("line3 0, 0, 0 = \t\t[" + createLine3(0, 0, 0) + "]");
		System.out.println("line4 0, 0, 0 = \t\t[" + createLine4(0, 0, 0) + "]");
		System.out.println("line5 0, 0, 0 = \t\t[" + createLine5(0, 0, 0) + "]");
	}
}

/* 
0, code=0
9, code=9
10, code=17
12, code=19
23, code=21
58, code=29
99, code=34
100, code=33
101, code=34
110, code=34
123, code=38
*/