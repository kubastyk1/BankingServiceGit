import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ClientAccount {

	private String name, lastName, pesel;
	private static float money = 0;
	private static int accountNumber = 0, counter = 0;
	private List<String> clients;
	private Path file = Paths.get("f:\\myfile.txt");
	private WritingReadingTxt ioAccount = new WritingReadingTxt();
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
		
		String[] table = ioAccount.chooseClient();
		money = Float.parseFloat(table[4]);
		Scanner scr = new Scanner(System.in);
		System.out.println("Stan konta wynosi: " + money + " $ ");
		System.out.println("Podaj kwot� do wp�aty: ");
		money += scr.nextFloat();
		System.out.println("Stan konta wynosi: " + money + " $ ");
		
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
	
	public boolean accountExist(){
		
		boolean exist;
		if(counter == 1)
			exist = true;
		else
			exist = false;
		return exist;
	}
	
}
