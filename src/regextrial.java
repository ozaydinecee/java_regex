import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

public class regextrial {
    public static void main(String args[]) throws IOException {
        String text;
        FileWriter fw = new FileWriter("output.txt");
        try
        {
//the file to be opened for reading
            FileInputStream fis=new FileInputStream("input.txt");
            Scanner sc=new Scanner(fis);    //file to be scanned
//returns true if there is another line to read
            while(sc.hasNextLine())
            {
                text=sc.nextLine();
                text=link(text);
                fw.write("Alteration 1:"+text+"\n");
                text=date(text);
                fw.write("Alteration 2:"+text+"\n");
                text=name(text);
                fw.write("Alteration 3:"+text+"\n");
                text=emailGenerator(text);
                fw.write("Alteration 4:"+text+"\n");
                text=concatenation(text);
                fw.write("Alteration 5:"+text+"\n");
                text=deleteDuplicate(text);
                fw.write("Alteration 6:"+text+"\n");
                text=mobile_number(text);
                fw.write("Alteration 7:"+text+"\n");
                text=militaryToStandart(text);
                fw.write("Alteration 8:"+text+"\n");
                text=apostrophe(text);
                fw.write("Alteration 9:"+text+"\n");
                text=pathConverter(text);
                fw.write("Alteration 10:"+text+"\n");
                text=citation(text);
                fw.write("Alteration 11:"+text+"\n");
                fw.write("\n Next Line\n");//returns the line that was skipped
            }
            sc.close();
            fw.close();//closes the scanner
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }



    /**
     * @param Name that is written at the end of the mail
     * @return adds email that is generated by the name as the MEF style.
     * @author aslıyılmaz
     * -Aslı Yılmaz
     * -Aslı Yılmaz
     * for contact yilmaza@mef.edu.tr
     */
    public static String emailGenerator(String text){

        Pattern p = Pattern.compile("-([A-Z])(\\w+)\\s([A-Z]\\w+)");
        Matcher m = p.matcher(text);
        if(m.find()) {
            String email = m.group(3);

            email += m.group(1);
            email += "@mef.edu.tr";
            email = email.toLowerCase();
            text = m.replaceAll("-$1$2 $3" + " (for contact " + email + " )");
        }
        System.out.println(text);

        return text;
    }




    /**
     * @author aslıyılmaz
     * @param military time
     * @return standard time
     */
    public static String militaryToStandart(String text){
        Pattern p = Pattern.compile("([0-9][0-9]):([0-9][0-9])\\s([A-Z][A-Z])");
        Matcher m = p.matcher(text);
        String textCopy=text;
        Matcher m1= p.matcher(textCopy);
        String checker=m1.replaceAll("$3");
        if (checker=="AM"||checker=="PM"){
            return text;
        }
        p = Pattern.compile("([0-9][0-9]):([0-9][0-9])");
        m = p.matcher(text);
        if(m.find()) {
            String hour = m.group(1);
            int clock = Integer.parseInt(hour);
            String time;
            if (clock > 12) {
                clock = clock - 12;
                time = "PM";
            } else {
                time = "AM";
            }
            text = m.replaceAll(clock + ":$2 " + time);
        }
        return text;
    }
    
    
        /**
     * Changes symbol between the numbers as a slash that indicate the date.
     *Input date can be expressed with .(point) or -(hyphen) ,output can be only with slash
     * @author eceozaydın
     */
    public static String date(String text){
        Pattern pat = Pattern.compile("(\\d{2})[\\.\\-](\\d{2})[\\.\\-](\\d{4})",
                Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(text);
        text=m.replaceAll("$1/$2/$3");
        m = pat.matcher(text);
        return text;
    }


    /**
     * @author: eceozaydin
     * whatever the format of the input strings
     *mobile_phone functions adds +90 to numbers groups.
     */
    public static String mobile_number(String text){
        Pattern pat = Pattern.compile(".?\\(?(\\d{3})[\\)\\- ]*(\\d{3})[)\\- ]*(\\d{2,4})[)\\- ]*(\\d{2})",  //one or more word characters,comma,single white space
                Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(text);
        text=m.replaceAll(" +90$1$2$3$4");
        m = pat.matcher(text);
        return text;
    }

    /**
     * Changes given name format
     * Surname , Title Name to Title Name Surname
     * @author eceozaydın
     */
    public static String name(String text){
        Pattern pat = Pattern.compile("(\\w+),\\s(Mr.|Ms.|Mrs.)\\s(\\w+)",  //([a-zA-Z]+)s([a-zA-Z]+)
                Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(text);
        text=m.replaceAll("$2 $3 $1");
        m = pat.matcher(text);
        return text;
    }
    /**
     * Takes necessary words in the sentence and returns words as a link
     * @return link that includes like .png and .com expressions
     * @author eceozaydin
     *
     */
    public static String link(String text){
        Pattern pat = Pattern.compile("((\\w*\\s)*)(\\w+\\.png|jpg)\\s(\\w*\\s)*(\\w+\\.(com|org))((\\w*\\s?\\.?)*)",
                Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(text);
        String link="";
        if (m.find()){
            link="(https://www."+m.group(5)+"//"+m.group(2)+")";
        }
        text+=link;
        return text;
    }

    /**
     * @author eceozaydın
     * Deletes duplicate word in the sentences.
     */
    public static String deleteDuplicate(String text){
        Pattern pat = Pattern.compile("(\\w+'?t?)\\s(\\1)",
                Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(text);
        text=m.replaceAll("$1");
        m = pat.matcher(text);
        return text;
    }

    
    /**
     * @author aslıyılmaz
     * @param grammarly mistaken apostrophe
     * @return corrected apostrophe
     */
    public static String apostrophe(String text) {
        //Separate ownership
        //Alice's and John's cars ----> Alice's car and John's car
        Pattern pat = Pattern.compile("([A-Z]\\w+)'s\\sand\\s([A-Z]\\w+)'s\\s(\\w+)s");
        Matcher m = pat.matcher(text);
        text = m.replaceAll("$1's $3 and $2's $3");
        //Common ownership
        //Alice's and John's car ----> Alice and John's car
        pat = Pattern.compile("([A-Z]\\w+)'s\\sand\\s([A-Z]\\w+)'s\\s(\\w+)");
        m = pat.matcher(text);
        text = m.replaceAll("$1 and $2's $3");
        return text;
    }

    /**
     * @param Citated sentence
     * @return Quoted form of the citation
     * I love Ece. (Aslı, 2021).
     * @author aslıyılmaz
     * Aslı says "I love Ece." in 2021.
     */
    public static String citation(String text){
        Pattern p = Pattern.compile("((\\w+\\s)*)\\((\\w+),\\s(\\w+)\\).");
        Matcher m = p.matcher(text);
        text=m.replaceAll("$3 says \"$1.\" in $4.");
        return text;
    }
    /**
     * @author aslıyılmaz
     * @param windows path
     * @return unix path
     */
    public static String pathConverter(String text) {
        Pattern pat = Pattern.compile("([A-Z]:)*(\\\\(\\w+)*)");
        Matcher m = pat.matcher(text);
        if(m.find()) {
            String unixPath = "/Volumes/Share";
            while (m.find()) {
                unixPath += "/" + m.group(3);
            }
            return text+("(for UNIX the path is "+unixPath+")");
        }
        return text;
    }

    /**
     * @author aslıyılmaz
     * @param x and y
     * @return X and Y
     */
    public static String concatenation(String text) {
        Pattern pat = Pattern.compile("([A-Z]\\w+)(\\sand\\s)([A-Z]\\w+)");
        Matcher m = pat.matcher(text);

        if(m.find()) {
            text = m.replaceAll(m.group(1).toUpperCase() + " and " + m.group(3).toUpperCase());
        }
        return text;
    }









}
