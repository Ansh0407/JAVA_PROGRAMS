class Lab21 {
    public static void main (String[] args) {
        String paragraph = "We realizes that while our workers were thriving, the surrounding villages were still suffering. It became our goal to uplift their quality of life as well. I remember seeing a family of 4 on a motorbike in the heavy Bombay rain — I knew I wanted to do more for these families who were risking their lives for lack of an alternative The alternative mentioned here is the Tata Nano, which soon after came as the worlds cheapest car on retail at a starting price of only Rs 1 lakh. These were the words of Ratan Tata in a recent post by Humans of Bombay which formed the basis of his decision to come up with a car like Tata Nano.";

        char[] extraString = new char[55];

        System.out.println("The paragraph is: \n" + paragraph);

        System.out.println("\nUsing charAt() method on the paragraph: \n" + paragraph.charAt(100));
        System.out.println("\nUsing compareTo() method on the paragraph:\n" + paragraph.compareTo("risking their lives for lack of an alternative"));
        System.out.println("\nUsing concat() method on the paragraph:\n" + paragraph.concat("This is the additional text that I am writting!"));
        System.out.println("\nUsing contains() method on the paragraph:\n" + paragraph.contains("Nano"));
        System.out.println("\nUsing endsWith() method on the paragraph:\n" + paragraph.endsWith("."));
        System.out.println("\nUsing equals() method on the paragraph:\n" + paragraph.equals("The Text can not be equal ofcourse!"));
        System.out.println("\nUsing equalsIgnoreCase() method on the paragraph:\n" + paragraph.equalsIgnoreCase("The Text still can not be equal ofcourse!"));
        System.out.println("\nUsing format() method on the paragraph:\n" + String.format("This is the units sold of the Tata Nano: %d", 36000));
        System.out.println("\nUsing getBytes() method on the paragraph:\n" + paragraph.getBytes());
        paragraph.getChars(50, 100, extraString, 0);
        System.out.println("\nUsing getChars() method on the paragraph:\n" + new String(extraString));
        System.out.println("\nUsing indexOf() method on the paragraph:\n" + paragraph.indexOf("Nano"));
        System.out.println("\nUsing intern() method on the paragraph:\n" + paragraph.intern());
        System.out.println("\nUsing isEmpty() method on the paragraph:\n" + paragraph.isEmpty());
        System.out.println("\nUsing join() method on the paragraph:\n" + String.join(paragraph, "This is the units sold of the Tata Nano: 30000"));
        System.out.println("\nUsing lastIndexOf() method on the paragraph:\n" + paragraph.lastIndexOf("Nano"));
        System.out.println("\nUsing length() method on the paragraph:\n" + paragraph.length());
        System.out.println("\nUsing replace() method on the paragraph:\n" + paragraph.replace("Tata", "TATA"));
        System.out.println("\nUsing replaceAll() method on the paragraph:\n" + paragraph.replaceAll("Nano", "NANO"));
        System.out.println("\nUsing split() method on the paragraph:\n" + paragraph.split("Nano", 2));
        System.out.println("\nUsing startsWith() method on the paragraph:\n" + paragraph.startsWith("Hello"));
        System.out.println("\nUsing substring() method on the paragraph:\n" + paragraph.substring(100));
        System.out.println("\nUsing toCharArray() method on the paragraph:\n" + new String(paragraph.toCharArray()));
        System.out.println("\nUsing toLowerCase() method on the paragraph:\n" + paragraph.toLowerCase());
        System.out.println("\nUsing toUpperCase() method on the paragraph:\n" + paragraph.toUpperCase());
        System.out.println("\nUsing trim() method on the paragraph:\n" + paragraph.trim());
        System.out.println("\nUsing valueOf() method on the paragraph:\n" + String.valueOf(paragraph));
    }
}