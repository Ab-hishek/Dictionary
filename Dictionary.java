import java.util.*;
import java.io.*;

class Dictionary{
    HashMap<String, String> hm = new HashMap<String, String>(); // Data structure to store the contents of the file
    Scanner s;
    String word,meaning; // global variables to word and meaning entered by the user

    // Constructor to allocate memory to the Scanner class object for user inputs
    Dictionary(){
        s= new Scanner(System.in);
    }

    // function to read data from the dictionary
    void readData(){
        try  
        {  
            File file=new File("dictionary.txt"); //creates a new file instance  
            FileReader fr=new FileReader(file); //reads the file  
            BufferedReader br=new BufferedReader(fr); //creates a buffering character input stream  
            String line; 

            // Taking inputs from the file line by line 
            while((line=br.readLine())!=null)  
            {  
                // storing each word and its meaing in HashMap 
                hm.put(line.substring(0,line.indexOf(":")),line.substring(line.indexOf(":"))); 
            }  
            fr.close(); //closes the stream and release the resources  
        }  
        //Handles any input/output exceptions in a file
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }  
    }

    // function to input a word and its meaning into the dictionary
    void insertWord(){
        word= s.nextLine();
        meaning=s.nextLine();
        meaning=": "+meaning;

        // Condition to check whether a the new word is already present in the dictionary or not
        if(hm.containsKey(word))
            System.out.println("Word already exists in the dictionary");

        // If the word is not present in the dictionary then the insert operation is performed
        else
        {
            hm.put(word,meaning);
            String sentence= word + meaning+"\n";
            try { 
                // Open given file in append mode. 
                BufferedWriter out = new BufferedWriter(new FileWriter("dictionary.txt", true)); 
                out.write(sentence);// Writing the new word and its meaning into the file 
                out.close(); // closes the stream and release the resources
                System.out.println(word+" successfully added to the dictionary"); 
            } 
            // Handling exceptions
            catch (IOException e) { 
                System.out.println("exception occoured" + e); 
            } 
        }
    }

    // function to find whether a word is present in the dictionary or not
    void searchWord(){
        word=s.nextLine();
        // condition to check the presence of the word in the file
        if(hm.containsKey(word.trim()))
            System.out.println("The meaning of the word "+ word+" is"+hm.get(word.trim()));
        else    
            System.out.println("The word is not present in the dictionary");
    }

    // function to make a string object containing all the contents that needs be written into the file
    String fileWriteContent(){
        String sentence="";
        // Getting an iterator 
        Iterator hmIterator= hm.entrySet().iterator();
        // Iterate through the hashmap 
        while (hmIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            String meaning = (String.valueOf(mapElement.getValue()));
            sentence+= mapElement.getKey() + meaning + "\n";
        }
        return sentence;
    }

    // function to make changes to the contents of the file
    void writeChanges(){
        try { 
                // Open given file in append mode. 
                BufferedWriter out = new BufferedWriter(new FileWriter("dictionary.txt"));
                String sentence =  fileWriteContent();
                out.write(sentence); 
                out.close(); 
            } 
            // Handling exceptions
            catch (IOException e) { 
                System.out.println("exception occoured" + e); 
            }
    }

    // function to update the meaning of a word in the file
    void updateWord(){
        word= s.nextLine();
        meaning=s.nextLine();
        meaning=": "+meaning;
        if(hm.containsKey(word)){
            hm.replace(word.trim(),meaning);
            writeChanges(); // fucntion to make the desired changes into the file
            System.out.println("The meaning of the word "+word+" is"+meaning);
        }
        else    
            System.out.println("The word "+word+" is not present in the dictionary hence can't be updated");
    }

    // function to delete a word and its meaning from a file
    void deleteWord(){
        word=s.nextLine();
        String removed;
        if(hm.containsKey(word)){
            removed=(String)hm.remove(word.trim());
            writeChanges();// calling the fucntion to make changes into the file
            System.out.println("The word "+word+" has been deleted from the dictionary");
        }
        else{
            System.out.println("There is so such word as "+word+" in the dictionary");
        }
    }

    // function to display the contents of the file
    void displayDictionary(){
        // Getting an iterator
        Iterator hmIterator= hm.entrySet().iterator();
        // Iterate through the hashmap
        while (hmIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            String meaning = (String.valueOf(mapElement.getValue()));
            System.out.println(mapElement.getKey() + meaning);
        }
    }

    // function to display the menu to the users
    void printMenu(){
        System.out.println("-------------------------------");
        System.out.println("\tMenu\t");
        System.out.println("1. Insert a word into the dictionary");
        System.out.println("2. Search a word from the dictionary");
        System.out.println("3. Update the meaning of a word");        
        System.out.println("4. Delete a word and its meaning from the dictionary");
        System.out.println("5. Display the Dictionary");
        System.out.println("6. Exit");
        System.out.println("-------------------------------");
    }

    // main function of the program
    public static void main(String args[]){
        Dictionary dict = new Dictionary(); // creating an object of the class
        dict.readData(); // reading data from the file

        Scanner sc = new Scanner(System.in);
        
        //String word,meaning;
        int choice;

        while(true){
            dict.printMenu();
            System.out.println("Enter your choice:");
            
            // to validate user inputs
            while(!sc.hasNextInt()){
                System.out.println("Error! Please enter a valid integer");
                sc.next();
            }
            choice=sc.nextInt();

            // different cases to handle the users's requirement
            switch(choice){
                case 1:
                    System.out.println("Enter the word and its meaning:");
                    dict.insertWord();
                    break;
                case 2:
                    System.out.println("Enter the word for which its meaning is to be searched:");
                    dict.searchWord();
                    break;
                case 3:
                    System.out.println("Enter the word and its meaning:");
                    dict.updateWord();
                    break;
                case 4:
                    System.out.println("Enter the word to be deleted:");
                    dict.deleteWord();
                    break;
                case 5:
                    dict.displayDictionary();
                    break;
                case 6:
                    System.out.println("-----Exiting-----");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice! Please choose a valid option");
            }
        }
    }
}