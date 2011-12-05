package downloader;

import java.io.File;
import java.util.ArrayList;
import scala.tools.nsc.MainGenericRunner;

public class Main { 
    public static void main (String[] args) {   
    	System.out.println(new File(".").getAbsolutePath());
        ArrayList<String> argList = new ArrayList<String>();
        argList.add("/Users/garethevans/eclipse/RallyKanbanData/bin/downloader.Runner");
        for (String s : args) {
            argList.add(s);
        }
        MainGenericRunner.main(argList.toArray(new String[0]));
    }
}