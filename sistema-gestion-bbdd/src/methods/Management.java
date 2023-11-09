package methods;

public class Management {
    public static boolean finishesInVowel(String text){
        return text.endsWith("a") || text.endsWith("e") || text.endsWith("i") || text.endsWith("o") || text.endsWith("u");
    }

    public static String SingularToPlural(String text){
        String convertedText = text.toLowerCase();
        if (convertedText.endsWith("s") || convertedText.endsWith("es") || convertedText.endsWith("ces") ||
                convertedText.endsWith("os") || convertedText.endsWith("is") || convertedText.endsWith("us") ||
                convertedText.endsWith("as")) {
            return text.substring(0, text.length() - obtenerLongitudSufijo(convertedText));
        };
        return text;
    }

    public static int obtenerLongitudSufijo(String texto) {
        // Devuelve la longitud del sufijo
        if (texto.endsWith("es") || texto.endsWith("ces")) {
            return 2;
        } else {
            return 1;
        }
    }
}
