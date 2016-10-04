package PRM; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLChar;
import com.jmatio.types.MLDouble;

import util.Aresta;
import util.Graph;
import util.Vertice;
public class Main {

	public static void main(String[] args) throws IOException {

		double x0,y0,xf,yf;
		x0 = 36.45;
		xf = 74;
		y0 = 121.45;
		yf = 185;
		System.out.println("DIGITE O NUMERO DE VERTICES EM X");
		Scanner sj1 = new Scanner(System.in);
		int DIV = sj1.nextInt();
		System.out.println("DIGITE O NUMERO DE VERTICES EM Y");
		Scanner sj2 = new Scanner(System.in);
		int DIV2 = sj2.nextInt();
		Graph grafo = new Graph(DIV , DIV2);
		System.out.println(grafo);	
		System.out.println("DIGITE O NUMERO DE VERTICES MÁXIMOS");
		Scanner sj = new Scanner(System.in);
		int N = sj.nextInt();
		System.out.println("DIGITE O NODO DESTINO");
		Scanner sp = new Scanner(System.in);
		String dst = sp.nextLine();
		System.out.println("DIGITE O NODO INICIAL");
		Scanner sl = new Scanner(System.in);
		String In = sl.nextLine();
		Graph Novo = new Graph(DIV , DIV2, N);

		int x1 = Integer.parseInt(In.split("\\.")[0]);
		int y1 = Integer.parseInt(In.split("\\.")[1]);
		int x2 = Integer.parseInt(dst.split("\\.")[0]);
		int y2 = Integer.parseInt(dst.split("\\.")[1]);



		do{


			System.out.println("DESEJA COLOCAR ALGUMA AREA DE OBSTÁCULOS? 1-SIM 0-NAO");
			Scanner sc = new Scanner(System.in);
			int z = sc.nextInt();
			if (z == 0) break;
			else{

				System.out.println("DIGITE A AREA EM QUE EXISTE UM OBSTACULO ( MAIOR E MENOR VERTICE)");
				System.out.println("MAIOR VERTICE");
				Scanner sd = new Scanner(System.in);
				String max = sd.nextLine();
				System.out.println("MENOR VERTICE");
				Scanner sk = new Scanner(System.in);
				String min = sk.nextLine();
				/*for (Aresta e : Novo.getArestas())
				{	if(e.getOrigem().getNome().equalsIgnoreCase(max) && e.getDestino().getNome().equalsIgnoreCase(min))
				{
					Novo.removeAresta(e);
					break;
				}}*/ 
				
				Novo.removeVertice(min, max);
				///System.out.println(Novo);
			}}while(true);
		File file = new File("C:\\Users\\Leo\\workspace\\PRM\\Caminho.txt");
		FileWriter esc = new FileWriter(file);
		BufferedWriter bf = new BufferedWriter(esc);
		System.out.println(Novo);
		ArrayList<Vertice>caminho = new ArrayList<Vertice>();
		System.out.println("REDUÇÃO DE CAMINHOS");
		try{
			caminho = Novo.reduçãoCaminho(Novo.getVertice((x2)+"."+y2), Novo.getVertice((x1)+"."+y1));
		}catch(StackOverflowError e){JOptionPane.showMessageDialog(null,"Caminho Não Possível");}
		System.out.println(caminho.size());
		double[][] list1 = new double [3][105];
		int i = 0;
		for (Vertice e : caminho)
		{	
			double X;
			try{
			System.out.println(e.getNome());
			int x = Integer.parseInt(e.getNome().split("\\.")[0]);
			
			
			if ( x == 0)
				X = x0;
			else     X = (((xf-x0)/(DIV-1)) * (x))+ (x0);
			bf.write(String.valueOf(X) + " " );
			list1[0][i] = X;   
			i++;
		}catch(NullPointerException x){JOptionPane.showMessageDialog(null,"Caminho Não Possível");
		}
		}
		
		bf.newLine();
		int k = 0;
		for (Vertice e : caminho)
		{	
			double Y;
			double y = Integer.parseInt(e.getNome().split("\\.")[1]);
			if(y == 0) Y = y0;
			else  Y = (((yf-y0)/(DIV2-1)) * (y)) + (y0);
			bf.write(String.valueOf(Y) + " " );
			list1[1][k] = Y;
			k ++;


		}
		bf.newLine();
		for (int z = 0 ; z < i ; z ++)
		{	double l = z;
			
			list1[2][z] = (l/10);
			bf.write(String.valueOf(l/10)+ " ");
			k ++;


		}
		MLDouble mlDouble = new MLDouble("Variaveis",list1 );

		bf.close();
		esc.close();



		ArrayList list = new ArrayList();
		list.add( mlDouble );
		new MatFileWriter( "mat_file.mat", list);




	}





}
