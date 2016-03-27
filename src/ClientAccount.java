import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.*;
import java.util.*;
import java.util.stream.Stream;

public class ClientAccount {

	public String name, lastName, pesel, adres;
	public static float money = 0;
	public static int accountNumber = 0;
	public static int counter = 0;
	List<String> clients;
	String filePath = "f:\\myfile.txt";
	Path file = Paths.get("f:\\myfile.txt");
	
	public void createAccount(){
		
		Scanner scr = new Scanner(System.in);
		do{
			System.out.println("Podaj imi�: ");
			name = scr.nextLine();
		}while(!name.matches("[a-zA-Z]+"));
		
		do{
			System.out.println("Podaj nazwisko: ");
			lastName = scr.nextLine();
		}while(!lastName.matches("[a-zA-Z]+"));
		
		do{
			System.out.println("Podaj PESEL: ");
			pesel = scr.nextLine();
		}while(!pesel.matches("[0-9]+") /*&& pesel.length() == 11*/);
		accountNumber += 1;
		System.out.println(accountNumber + " :::: Imie: " + name + " Nazwisko: " + lastName + " PESEL: " + pesel);
		counter = 1;
		saveInTxt(name, lastName, pesel, money, accountNumber);
	}
	
	public void paymentOnAccount(){
		
		if(counter == 1){
			Scanner scr = new Scanner(System.in);
			System.out.println("Stan konta wynosi: " + money + " $ ");
			System.out.println("Podaj kwot� do wp�aty: ");
			money += scr.nextFloat();
			System.out.println("Stan konta wynosi: " + money + " $ ");
		}else
			System.out.println("Najpierw musisz za�o�y� konto ");
	}
	
	public void paymentOffAccount(){
		
		Scanner scr = new Scanner(System.in);
		float sum = 0;
		System.out.println("Stan konta wynosi: " + money + " $ ");
		System.out.println("Podaj kwot� do wyp�aty: ");
		sum = scr.nextFloat();
		if(money < sum){
			System.out.println("Niewystarczaj�ca ilo�� pieni�dzy. Wpisz 0 by wyj��.");
			paymentOffAccount();
		}else{
			money -= sum;
			System.out.println("Stan konta wynosi: " + money + " $ ");
		}
	}
	
	public void saveInTxt(String name, String lastName, String pesel, float money, int accountNumber) {
		
		String my_money = Float.toString(money);
		String my_number = Integer.toString(accountNumber);
		clients = Arrays.asList(my_number, name, lastName, pesel, my_money);
		if(!Files.exists(file)){
			try {
			    // Create the empty file with default permissions, etc.
			    Files.createFile(file);
			} catch (FileAlreadyExistsException x) {
			    System.err.format("file named %s" + " already exists%n", file);
			} catch (IOException x) {
			    // Some other sort of failure, such as permissions.
			    System.err.format("createFile error: %s%n", x);
			}
		}
		
		try {
			Files.write(file, clients, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void showClients(){
		
		try {
			String[] table = new String[5];
			int i = 0;
		    FileReader fileReader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    String textLine = bufferedReader.readLine();
		    do {
		    	if(i != 5){
		    		table[i] = textLine;
		    		textLine = bufferedReader.readLine();
		    		i++;
		    	}else {
		    		for(i = 0; i < 5; i++)
		    			System.out.print(table[i] + " - ");
		    		System.out.println();
		    		i = 0;
		    	}
		    } while (textLine != null || i == 5);
		    
		}catch (IOException e){
			e.printStackTrace();
		  } /*finally {
		    bufferedReader.close();
		  }*/
	}
	
	public void readFile() {
		
		
		
		 FileReader fr = null;
	      LineNumberReader lnr = null;
	      String str;
	      int i;
	      
	      try{
	         // create new reader
	         fr = new FileReader(filePath);
	         lnr = new LineNumberReader(fr);
	   
	         // read lines till the end of the stream
	         while((str=lnr.readLine())!=null)
	         {
	            i=lnr.getLineNumber();
	            System.out.print("("+i+")");
	                  
	            // prints string
	            System.out.println(str);
	            lnr.setLineNumber(i + 5);
	         }
	      }catch(Exception e){
	         
	         // if any error occurs
	         e.printStackTrace();
	      }/*finally{
	         
	         // closes the stream and releases system resources
	         if(fr!=null)
	            fr.close();
	         if(lnr!=null)
	            lnr.close();
	      }*/
	   }
	
	
	public boolean accountExist(){
		
		boolean exist;
		if(counter == 1)
			exist = true;
		else
			exist = false;
		return exist;
	}
	
}
