import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ManipulandoJson {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Gson gson = new GsonBuilder().create();
		String path = "C:\\Users\\Julio\\workspace\\manipulando-json\\funcionarios.json";
		InputStream is = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		ArrayList<String> arrayListLinha = new ArrayList<>();
		ArrayList<String> arrayListFuncionario = new ArrayList<String>();
		ArrayList<Funcionario> funcionario = new ArrayList<Funcionario>();
		ArrayList<Funcionario> chineses = new ArrayList<Funcionario>();
		ArrayList<Funcionario> mulheresChinesas = new ArrayList<Funcionario>();
		ArrayList<Funcionario> menorSalarioMulherChinesa = new ArrayList<Funcionario>();

		String linha = null;
		while ((linha = br.readLine()) != null) {
			arrayListLinha.add(linha.replace("[", ""));
		}

		arrayListLinha.forEach(x -> arrayListFuncionario.add(x.substring(0, x.length() - 1)));
		arrayListFuncionario.forEach(x -> funcionario.add(gson.fromJson(x, Funcionario.class)));

		funcionario.forEach(x -> {
			Funcionario func = x.getPais().equals("China") ? x : null;
			Boolean func2 = func != null ? chineses.add(func) : false;
		});

		chineses.forEach(x -> {
			Funcionario func = x.getGenero().equals("F") ? x : null;
			Boolean func2 = func != null ? mulheresChinesas.add(func) : false;
		});

		Stream<Funcionario> moradoresDaChina = funcionario.stream().filter(x -> x.getPais().equals("China"));
		// moradoresDaChina.forEach(x -> chineses.add(x));
		Stream<Funcionario> mulheres = chineses.stream().filter(x -> x.getGenero().equals("F"));
		// mulheres.forEach(m -> mulheresChinesas.add(m));
		menorSalarioMulherChinesa
				.add(mulheresChinesas.stream().reduce((p1, p2) -> p1.getSalario() < p2.getSalario() ? p1 : p2).get());

		System.out.println("Nomes dos Chineses: ");
		chineses.forEach(x -> System.out.println(x.getNome()));
		System.out.println("Quantidade de Chineses: " + chineses.size());
		System.out.println("Nomes apenas das mulheres Chinesas: ");
		mulheresChinesas.forEach(x -> System.out.println(x.getNome()));
		System.out.println("Quantidade de mulheres Chinesas: " + mulheresChinesas.size());
		System.out.println("Mulher Chinesa com menor salário: ");
		menorSalarioMulherChinesa.forEach(x -> System.out.println(x.getNome()));
		System.out.println("Valor do salário: ");
		menorSalarioMulherChinesa.forEach(x -> System.out.println(x.getSalario()));
	}
}
