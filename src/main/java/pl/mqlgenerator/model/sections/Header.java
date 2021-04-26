package pl.mqlgenerator.model.sections;

public class Header {

    public static String getHeader(int MAGICMA, String author, String link, String description){
        return  "//----------------------------------------------------------------------\n" +
                "//|   "+description+"                                                   \n" +
                "//|   "+author+"                                                        \n" +
                "//|   "+link+"                                                          \n" +
                "//----------------------------------------------------------------------\n" +
                "#property copyright   "+'"'+author+'"'+"\n" +
                "#property link        "+'"'+link+'"'+"\n" +
                "#property description "+'"'+description+'"'+"\n" +
                "#property strict \n"+
                "\n" +
                "#define MAGICMA  "+MAGICMA+"\n"+
                "\n";
    }

}
