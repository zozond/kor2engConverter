import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

	// 초성
	public static final String[] CHOSUNG =             { "ㄱ", "ㄲ", "ㄴ",     "ㄷ", "ㄸ", "ㄹ",       "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",	"ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	public static final String[] ENG_MAPPING_CHOSUNG = { "r", "R", "s", "e", "E", "f", "a", "q", "Q", "t", "T", "d", "w", "W", "c", "z", "x", "v", "g" };

	// 중성
	public static final String[] JUNGSUNG =             { "ㅏ", "ㅐ", "ㅑ",     "ㅒ", "ㅓ", "ㅔ", "ㅕ",    "ㅖ", "ㅗ", "ㅘ",     "ㅙ", "ㅚ", "ㅛ",     "ㅜ", "ㅝ", "ㅞ",     "ㅟ", "ㅠ", "ㅡ",   "ㅢ", "ㅣ" };
	public static final String[] ENG_MAPPING_JUNGSUNG = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl",
			"y", "n", "nj", "np", "nl", "b", "m", "ml", "l" };

	// 종성
	public static final String[] JONGSUNG = { "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	public static final String[] ENG_MAPPING_JONGSUNG = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
			"ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };

	// 무시해야 하는 케이스
	public static final String[] IGNORE_CHARACTERS = { "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=",
			"[", "]", "\\", ";", "\"", ".", "/", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "{",
			"}", "|", ":", "\'", "<", ">", "?", " " };
	
	public static Set<String> IgnoreInfo = null;
	public static Map<String, Integer> ChosungInfo = null;
	public static Map<String, Integer> JungsungInfo = null;
	public static Map<String, Integer> JongsungInfo = null;
	public static Map<String, Integer> ChosungInfoKor = null;
	public static Map<String, Integer> JungsungInfoKor = null;
	public static Map<String, Integer> JongsungInfoKor = null;
	
	public static void setInfo() {
		if (IgnoreInfo == null) {
			IgnoreInfo = getIgnoreInfo();
		}
		
		if (ChosungInfo == null) {
			ChosungInfo = getChosungInfo();
		}

		if (JungsungInfo == null) {
			JungsungInfo = getJungsungInfo();
		}

		if (JongsungInfo == null) {
			JongsungInfo = getJongsungInfo();
		}
		
		if (ChosungInfoKor == null) {
			ChosungInfoKor = getChosungInfoKor();
		}

		if (JungsungInfoKor == null) {
			JungsungInfoKor = getJungsungInfoKor();
		}

		if (JongsungInfoKor == null) {
			JongsungInfoKor = getJongsungInfoKor();
		}
	}

    /* 실제 변환해주는 함수 */
	public static String convertKor2Eng(String str) {
		String parseStr = parseJamo(str);
		
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		while (i < parseStr.length()) {
			String s = parseStr.substring(i, i + 1);
			if (IgnoreInfo.contains(s)) {
				sb.append(s);
				i++;
				continue;
			}
			
			if(ChosungInfoKor.containsKey(s)) {
				sb.append(ENG_MAPPING_CHOSUNG[ChosungInfoKor.get(s)]);	
			}else if(JungsungInfoKor.containsKey(s)) {
				sb.append(ENG_MAPPING_JUNGSUNG[JungsungInfoKor.get(s)]);
			}else if(JongsungInfoKor.containsKey(s)) {
				sb.append(ENG_MAPPING_JONGSUNG[JongsungInfoKor.get(s)]);
			}
			
			i++;
		}

		return sb.toString();
	}
	
    /* 자음과 모음으로 분리 시켜주는 함수 */
	public static String parseJamo(String str) {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		while (i < str.length()) {
			char s = str.charAt(i);
			if(IgnoreInfo.contains(s + "")) {
				sb.append(s);
				i++;
				continue;
			}
			
			if(s >= 0x3131 && s <= 0x3131 + 36) {
				sb.append(s);
			}else if(s >= 0x314F && s <= 0x314F + 58) {
				sb.append((char) 0);
			}else if(0xAC00 <= s  && s <= 0xD7AF ) {
				int uniVal = s - 0xAC00;
	            int cho = ((uniVal - (uniVal % 28))/28)/21;
	            int jung = ((uniVal - (uniVal % 28))/28) % 21;
	            int jong = (uniVal % 28);
	            
	            sb.append(CHOSUNG[cho]);
	            sb.append(JUNGSUNG[jung]);
	            if(jong != 0) sb.append(JONGSUNG[jong-1]);
			}
			
			i++;
		}
		

		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
        setInfo();
		System.out.println(convertKor2Eng("싢"));
	}
}
