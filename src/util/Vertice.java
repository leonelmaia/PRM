package util;



import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Vertice implements Comparable<Vertice> {

	private int distancia;
	int numero = 0;
	private String nome;
	private ArrayList<Aresta> adj;
	
	public Vertice(String name) {
		int x1 = Integer.parseInt(name.split("\\.")[0]);
		int y1 = Integer.parseInt(name.split("\\.")[1]);
		nome = name;
		adj = new ArrayList<Aresta>();
		// restrictions = nome + ": I{} N{} S{} E{} W{}";
	}

	
	

	private String opToBinary(String ports) {
		char[] outOp = { '0', '0', '0', '0', '0' };
		char[] port = ports.toCharArray();

		for (char pt : port) {
			switch (pt) {
			case 'E':
				outOp[4] = '1';
				break;
			case 'W':
				outOp[3] = '1';
				break;
			case 'N':
				outOp[2] = '1';
				break;
			case 'S':
				outOp[1] = '1';
				break;
			case 'I':
				outOp[0] = '1';
				break;
			}
		}

		return String.valueOf(outOp);
	}

	public Aresta getAresta(Vertice destino) {
		for (Aresta v : adj)
			if (v.getDestino().getNome().equals(destino.getNome()))
				return v;

		return null;

	}
	public boolean  VerifgetAresta(Vertice destino) {
		for (Aresta v : adj)
			if (v.getDestino().getNome().equals(destino.getNome()))
				return true;

		return false;

	}
	
	
	

	public ArrayList<Aresta> getArestas() {
		return adj;
	}

	public void addAdj(Aresta e) {

		adj.add(e);
	}

	public ArrayList<Aresta> getAdj() {

		return this.adj;

	}

	public Aresta getAdj(String color) {
		for (Aresta a : this.adj)
			if (a.getCor().equals(color))
				return a;

		System.out.println("ERROR : There isn't a Op " + color + "?");
		return null;
	}

	public String getNome() {

		return this.nome;

	}

	

	public boolean isIn(String min, String max) {
		int xMin = Integer.valueOf(min.split("\\.")[0]);
		int yMin = Integer.valueOf(min.split("\\.")[1]);
		int xMax = Integer.valueOf(max.split("\\.")[0]);
		int yMax = Integer.valueOf(max.split("\\.")[1]);

		int x = Integer.valueOf(nome.split("\\.")[0]);
		int y = Integer.valueOf(nome.split("\\.")[1]);

		if((x <= xMax && x >= xMin) && (y <= yMax && y >= yMin))return true;
		else return false;
	}

	

	public static String sortStrAlf(String input) {
		char[] ip1 = input.toCharArray();
		Arrays.sort(ip1);

		return String.valueOf(ip1);
	}

	
	
	public void checkIsolation(ArrayList<Vertice> alc) {
		if (!alc.contains(this))
			alc.add(this); // Adiciona primeiro core analisado aos alcancaveis
		for (Aresta adj : adj) {
			// So adiciona aos alcancaveis cores que ainda nao foram adicionados
			if (alc.contains(adj.getDestino()))
				continue;
			Vertice neigh = adj.getDestino();
			alc.add(neigh);
			// checa para vizinhos
			neigh.checkIsolation(alc);
		}
	}

	
	

	public int compareTo(Vertice outroVertice) {
		if (this.distancia < outroVertice.distancia) {
			return -1;
		}
		if (this.distancia > outroVertice.distancia) {
			return 1;
		}
		return 0;
	}

}