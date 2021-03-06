import java.util.*;

public class ClientAccount {

	private String name, lastname, pesel;
	private static double money = 0;
	private static int accountnumber = 0;
	private WritingReadingTxt ioAccount = new WritingReadingTxt();
	private WritingReadingExcel excel = new WritingReadingExcel();
	
	public void createAccount(){
		
		try(Scanner scr = new Scanner(System.in)){
		do{
			System.out.println("Podaj imi�: ");
			name = scr.nextLine();
		}while(!name.matches("[a-zA-Z]+"));
		
		do{
			System.out.println("Podaj nazwisko: ");
			lastname = scr.nextLine();
		}while(!lastname.matches("[a-zA-Z]+"));
		
		do{
			System.out.println("Podaj PESEL: ");
			pesel = scr.nextLine();
		}while(!pesel.matches("[0-9]+") /*&& pesel.length() == 11*/);
		accountnumber = ioAccount.getAccountNumber() + 1;
		System.out.println(accountnumber + " :::: Imie: " + name + " Nazwisko: " + lastname + " PESEL: " + pesel);
		//ioAccount.saveInTxt(name, lastName, pesel, money, accountNumber);
		String[] strtab = {Integer.toString(accountnumber), name, lastname, pesel, Double.toString(money)};
		excel.writeExcel(strtab);
		}
		
	}
	
	public void paymentOnAccount(){
		
		try(Scanner scr = new Scanner(System.in)){
		//String[] table = ioAccount.chooseClient();
		Object[] table = excel.chooseClient();
		//double oldMoney = (double) table[4];
		String oldmoney = (String) table[4];
		double oldMoney = Double.parseDouble(oldmoney);
		System.out.println("zombie");
		//double oldMoney = Double.parseDouble(table[4]);
		System.out.println("Stan konta wynosi: " + oldMoney + " $ ");
		System.out.println("Podaj kwot� do wp�aty: ");
		money = oldMoney + scr.nextDouble();
		System.out.println("Stan konta wynosi: " + money + " $ ");
		//ioAccount.writePayment(Integer.parseInt(table[0]), oldMoney, money);
		excel.updateExcel(table, money);
		}catch(InputMismatchException e){
			e.printStackTrace();
		}
	}
	
	public void paymentOffAccount(){
		
		try(Scanner scr = new Scanner(System.in)){
		//String[] table = ioAccount.chooseClient();
		Object[] table = excel.chooseClient();
		//double oldMoney = Double.parseDouble(table[4]);
		double oldMoney = (double) table[4];
		double sum = 0;
		System.out.println("Stan konta wynosi: " + money + " $ ");
		System.out.println("Podaj kwot� do wyp�aty: ");
		sum = scr.nextFloat();
		if(oldMoney < sum){
			System.out.println("Niewystarczaj�ca ilo�� pieni�dzy. Wpisz 0 by wyj��.");
			paymentOffAccount();
		}else{
			money = oldMoney - sum;
			System.out.println("Stan konta wynosi: " + money + " $ ");
		}
		//ioAccount.writePayment(Integer.parseInt(table[0]), oldMoney, money);
		excel.updateExcel(table, money);
		}catch(InputMismatchException e){
			e.printStackTrace();
		}
	}
	
	
}
