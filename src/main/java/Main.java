import java.io.*;
import java.util.ArrayList;

public class Main {

    public static ArrayList<File> javaDirs = new ArrayList<File>();
    public static ArrayList<File> uniqueJavaDirs = new ArrayList<File>();

    static void RecursivePrint(File[] arr,int index,int level)
    {
        // terminate condition
        if(index == arr.length) {
            return;
        }
        // tabs for internal levels
//        for (int i = 0; i < level; i++)
//            System.out.print("\t");

        // for files
        if(arr[index].isFile()) {
            if(arr[index].getName().endsWith(".java")) {
                javaDirs.add(arr[index]);
                //System.out.println(arr[index].getName());


            }
            //System.out.println(arr[index].getName());


        }

        // for sub-directories
        else if(arr[index].isDirectory())
        {
            //System.out.println("[" + arr[index].getName() + "]");

            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1);
        }

        // recursion for main directory
        RecursivePrint(arr,++index, level);
    }



    // Driver Method
    public static void main(String[] args) throws IOException
    {
        // Provide full path for directory(change accordingly)
        //String maindirpath = "C:\\Users\\deyam\\Downloads\\commons-io-master\\commons-io-master";
        String maindirpath = args[0];
        // File object
        File maindir = new File(maindirpath);

        if(maindir.exists() && maindir.isDirectory())
        {
            // array for files and sub-directories
            // of directory pointed by maindir
            File arr[] = maindir.listFiles();

            //System.out.println("**********************************************");
            //System.out.println("Files from main directory : " + maindir);
            //System.out.println("**********************************************");

            // Calling recursive method
            RecursivePrint(arr,0,0);
        }
//        System.out.println("-------All Java File Listing starts----------");
//        for (File x: javaDirs )
//            System.out.println("Java Files: "+x.getName());
        //System.out.println("");
          //System.out.println("-------All Java File Listing ends, and size is:"+ javaDirs.size());
//        System.out.println("-------------------------------------------");
          listUniqFile();
//        System.out.println("-------All Unique Java File Listing starts----------");
//        for (File x: uniqueJavaDirs ) {
//            System.out.println("Java Files: "+x.getName());
//        }
         // System.out.println("-------All Unique Java File Listing ends, and size is:"+ uniqueJavaDirs.size());
//        System.out.println("-------------------------------------------");

//        System.out.println("-------------------------------------------");
        int printary [] = loc();
//        System.out.println("-------Total number of blank lines----------: "+ printary[0]);
//        System.out.println("-------Total number of comment lines----------: "+ printary[1]);
//        System.out.println("-------Total number of code lines----------: "+ printary[2]);
//        System.out.println("-------------------------------------------");

        System.out.print(javaDirs.size()+"-"+uniqueJavaDirs.size()+"-"+printary[0]+"-"+printary[1]+"-"+printary[2]);
    }



    private static int[] loc() throws IOException {
        int blankLineNum = 0;
        int commentLineNum = 0;
        int codeLineNum = 0;

        int ary [] = new int[3];
        ary[0]=blankLineNum;
        ary[1]=commentLineNum;
        ary[2]=codeLineNum;

        for (int i = 0; i < uniqueJavaDirs.size(); i++) {
            try {
                BufferedReader reader1 = new BufferedReader(new FileReader(uniqueJavaDirs.get(i)));

                String line1 = reader1.readLine();
                line1 = line1.trim();


                while (line1 != null) {
                    line1 = line1.trim();
                    if (line1.equals("")) {
                        blankLineNum++;
                    }
//                    else if(line1.trim().startsWith("//")||line1.trim().startsWith("/*")||line1.trim().startsWith("*")||line1.trim().startsWith("*/")||line1.trim().startsWith("'*\'")){
//                        commentLineNum++;
//                    }
                    else if(line1.startsWith("//")){
                        commentLineNum++;
                    }
                    else if(line1.startsWith("/*")){
                        commentLineNum++;
                        while(line1 != null && !line1.endsWith("*/"))
                        {
                            line1 = reader1.readLine();
                            line1 = line1.trim();
                            commentLineNum++;
                        }
                    }
                    else {
                        codeLineNum++;
                    }


                    line1 = reader1.readLine();


                }

                reader1.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        ary[0]=blankLineNum;
        ary[1]=commentLineNum;
        ary[2]=codeLineNum;
        return ary;
    }




    public static void listUniqFile() {
        boolean b=false;
        uniqueJavaDirs.add(javaDirs.get(0));
        if(javaDirs.size()==0) {
            System.out.print("No file");
        }else if(javaDirs.size()==1) {
            System.out.print("Only one file");
        }else {
            for(int i=1; i<javaDirs.size();i++) {
                for(int j=0; j<uniqueJavaDirs.size();j++) {
                    try {
                        if(areEqual(javaDirs.get(i),uniqueJavaDirs.get(j))) {
                            b=true;
                            break;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(b==false) {
                    uniqueJavaDirs.add(javaDirs.get(i));
                }
                b=false;
            }
        }
    }

    public static boolean areEqual(File f1, File f2) throws IOException {
        // TODO Auto-generated method stub
        //boolean b=false;
        //open and read a file

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(f1));

            BufferedReader reader2 = new BufferedReader(new FileReader(f2));

            String line1 = reader1.readLine();

            String line2 = reader2.readLine();

            boolean areEqual = true;

            int lineNum = 1;

            while (line1 != null || line2 != null)
            {
                if(line1 == null || line2 == null)
                {
                    areEqual = false;

                    break;
                }
                else if(! line1.equalsIgnoreCase(line2))
                {
                    areEqual = false;

                    break;
                }

                line1 = reader1.readLine();

                line2 = reader2.readLine();

                lineNum++;
            }

            if(areEqual)
            {
                // System.out.println("Two files have same content.");
                reader1.close();

                reader2.close();

                return true;
            }
            else
            {
                //  System.out.println("Two files have different content. They differ at line "+lineNum);

                //  System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
                reader1.close();

                reader2.close();

                return false;
            }

            // reader1.close();

            // reader2.close();


        }
        catch(FileNotFoundException e) {

        }
        catch(IOException e) {

        }

//		   for (File x: uniqueJavaDirs ) {
//				//System.out.println("Java Files: "+x.getName());
//			}
        return false;

    }


    public static void listUniqFile() {
        boolean b=false;
        uniqueJavaDirs.add(javaDirs.get(0));
        if(javaDirs.size()==0) {
            System.out.print("No file");
        }else if(javaDirs.size()==1) {
            System.out.print("Only one file");
        }else {
            for(int i=1; i<javaDirs.size();i++) {
                for(int j=0; j<uniqueJavaDirs.size();j++) {
                    try {
                        if(areEqual(javaDirs.get(i),uniqueJavaDirs.get(j))) {
                            b=true;
                            break;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(b==false) {
                    uniqueJavaDirs.add(javaDirs.get(i));
                }
                b=false;
            }
        }
    }
}






