import java.util.*;
import java.io.*;

class Dictionary{
    static String filepath="D:\\Mayadata\\Dictionary.csv";
    // To take user inputs for any of the cases
    static String input()  
    {
        Scanner sc = new Scanner(System.in);
        String wordToAddded=sc.next();
        return wordToAddded; 
    }

    // To add a word to the dictionary
    void add( String wordToAddded,File file){
        try { 
            // Open given file in append mode. 
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true)); 
            out.write(wordToAddded+"\n"); 
            out.close(); 
        } 
        // Handling exceptions
        catch (IOException e) { 
            System.out.println("exception occoured" + e); 
        } 
    }

    // To search for a particular word in the dictionary
    boolean search(String searchWord, File file){
        try{
            Scanner csvFile = new Scanner(file);
            csvFile.useDelimiter("[,\n]"); //sets the delimiter pattern  
            while (csvFile.hasNext())//returns a boolean value  
            {  
                String s=csvFile.next();

                if(s.equals(searchWord))
                    return true;
            }
            csvFile.close();
        }
        // To handle file exception
        catch(FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    // To delete a word from the dictionary
    void delete(String deleteWord, File file){
        String tempfile="D:\\Mayadata\\temp.csv";
        File newFile= new File(tempfile);

        if(!search(deleteWord,file)){
            System.out.println("The word which you want to delete is not present in the dictionary");
            return;
        }
        else{
            try{
                FileWriter fw = new FileWriter(tempfile,true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                Scanner csvFile = new Scanner(file);
                csvFile.useDelimiter("[,\n]"); //sets the delimiter pattern  
                while (csvFile.hasNext())//returns a boolean value  
                {  
                    String s=csvFile.next();

                    if(!s.equals(deleteWord))
                        pw.print(s);
                }
                csvFile.close();
                pw.flush();
                pw.close();
                file.delete();
                File dump = new File(filepath);
                newFile.renameTo(dump);
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            // To handle file input/output errors
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
    }

    public static void main(String args[])throws IOException
    {
        Dictionary dict = new Dictionary();
        File file = new File(filepath);
        HashSet<String> hs = new HashSet<String>();
        String addWord,searchWord,deleteWord;
        int choice;
        boolean loop=true;
        Scanner sc = new Scanner(System.in);
        while(loop){
            System.out.println("1. Add a word to the dictionary");
            System.out.println("2. Search for a word in the dictionary");
            System.out.println("3. Remove a word from the dictionary");
            System.out.println("To exit press 0");
            System.out.println("Enter your choice:");
            choice=sc.nextInt();
            switch(choice){
                case 0:
                    loop=false;
                    break;
                case 1:
                    System.out.println("Enter a word to be added:");
                    dict.add(input(),file);
                    break;
                case 2:
                    System.out.println("Enter the word to be searched:");
                    if(dict.search(input(), file))
                        System.out.println("Yayy! The word you are looking for is present in the dictionary");
                    else
                        System.out.println("Sorry! The word you are looking for is not present in the dictionory");
                    break;
                case 3:
                    System.out.println("Enter the word to be searched:");
                    dict.delete(input(),file);
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }  
} 