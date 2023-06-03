package utils;

public class LocalTextFormatter {
	
	public static int formatPrice(String price) {
		return Integer.valueOf(price.replaceAll("₹", "").replaceAll(",", ""));
	}

}
