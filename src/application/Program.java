package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
	
		//c:\temp\desafioAnaliseDeVendas1.csv
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]),Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3])
						, Double.parseDouble(fields[4])));
				line = br.readLine();
			}
		
			List<Sale> averagePrice = list.stream().filter(p-> p.getYear() ==2016)
					.sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice())).limit(5)
					.collect(Collectors.toList());
			
			double loganSales = list.stream().filter(p -> p.getSeller().equals("Logan"))
					.filter(p -> p.getMonth() == 1 || p.getMonth() == 7 ).map(p -> p.getTotal())
					.reduce((double) 0, (x,y) -> x+y);
			
			System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
			averagePrice.forEach(System.out::println);
			System.out.printf("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f ", loganSales);
		
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	
		sc.close();
	}
}
