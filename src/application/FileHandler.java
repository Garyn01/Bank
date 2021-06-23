package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class FileHandler {
    static void saveClients(SortedMap<Integer,Account> clients) throws IOException{ //save the client map to a file
        FileWriter fw = new FileWriter("Clients.csv");
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
        clients.forEach((accNumber,account) -> {
            try {
                account.setMoney(BigDecimal.valueOf(account.getMoney()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                fw.write(accNumber.toString()+';'+account.getName()+';'+account.getSurname()+';'+account.getPESEL()+';'+account.getCity()+';'+account.getRoad()+';'+ account.getMoney().toString()+'\n');
            } catch (IOException e) {
                System.out.println("Something went wrong while writing the file. Please contact your system admin.");
                e.printStackTrace();
            }
        });
        fw.close();
    }
    public static SortedMap<Integer,Account> readClients() throws FileNotFoundException { //load clients into a map and return it
        File file;
        SortedMap<Integer,Account> clients = new TreeMap<>();
        file = new File("Clients.csv");
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()){
            String[] line = sc.nextLine().split(";");
            Account tempAcc=new Account(line[1],line[2],Integer.parseInt(line[3]),line[4],line[5],Double.parseDouble(line[6]));
            clients.put(Integer.parseInt(line[0].strip()),tempAcc);
        }
        sc.close();
        return clients;
    }
}
