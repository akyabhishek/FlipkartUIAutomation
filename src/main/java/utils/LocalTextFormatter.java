package utils;

public class LocalTextFormatter {
	// converts and returns the integer value of price
	public static int formatPrice(String price) {
		return Integer.valueOf(price.replaceAll("₹", "").replaceAll(",", "").replaceAll("?", ""));
	}

}
