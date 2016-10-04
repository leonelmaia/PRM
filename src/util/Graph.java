package util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SingleSelectionModel;

public class Graph {
	boolean debug = true;
	protected static String[] ports = { "N", "S", "E", "W" };
	private final static String[] RoundRobin = { "N", "E", "S", "W" };
	private static int RRIndex[];
	ArrayList<Vertice> vertices;
	public ArrayList<Aresta> arestas;
	int dimX;
	int dimY;

	public Graph() {
		vertices = new ArrayList<>();
		arestas = new ArrayList<>();
	}




	public Graph(int dX , int dY)
	{

		vertices = new ArrayList<>();
		arestas = new ArrayList<>();

		dimX=dX;
		dimY = dY;

		//Adiciona Vertices
		for(int x=0; x<dimX; x++)
			for(int y=0; y<dimY; y++)
				addVertice(x+"."+ y);

		//Adiciona Arestas
		for(int y=0; y<dimY; y++)
			for(int x=0; x<dimX; x++)
			{
				if(contem(x+"."+(y+1)))
					addAresta(getVertice(x+"."+y), getVertice(x+"."+(y+1)), ports[0]);
				if(contem(x+"."+(y-1)))
					addAresta(getVertice(x+"."+y), getVertice(x+"."+(y-1)), ports[1]);
				if(contem((x+1)+"."+y)) 
					addAresta(getVertice(x+"."+y), getVertice((x+1)+"."+y), ports[2]);	
				if(contem((x-1)+"."+y)) 
					addAresta(getVertice(x+"."+y), getVertice((x-1)+"."+y), ports[3]);	
			}				


	}




	public   boolean contem(String vertice) {

		for (int i = 0; i < vertices.size(); i++) {

			if (vertice.equalsIgnoreCase(vertices.get(i).getNome())) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Vertice> getVertices() {

		return this.vertices;

	}

	public ArrayList<Aresta> getArestas() {
		return this.arestas;
	}

	public Vertice getVertice(String nomeVertice) {
		Vertice vertice = null;

		for (Vertice v : this.vertices) {
			if (v.getNome().equals(nomeVertice))
				vertice = v;
		}

		if (vertice == null) {
			//System.out.println("Vertice: " + nomeVertice + " nao encontrado");
			return null;
		}

		return vertice;
	}

	public Aresta getAresta(String nomeVerticeOri , String nomeVerticeDest ) {
		Aresta aresta = null;

		for (Aresta a : this.arestas) {
			if (a.starting.getNome().equals(nomeVerticeOri)&& a.ending.getNome().equals(nomeVerticeDest))
				aresta = a;
		}

		if (aresta == null) {
			System.out.println("Aresta: " + nomeVerticeOri + "---to---"+ nomeVerticeDest + " nao encontrado");
			return null;
		}
		return aresta;
	}

	private void addVertice(String nome) {
		Vertice v = new Vertice(nome);
		vertices.add(v);
	}

	protected boolean addArestaPossible(String origem, String destino) 
	{	

		if (AreNeighbours(origem , destino) == true    )
			return true;
		else 
			return false;
	}


	protected void addAresta(Vertice origem, Vertice destino) 
	{
		Aresta e = new Aresta(origem, destino);
		origem.addAdj(e);
		arestas.add(e);

	}

	protected void addAresta(Vertice origem, Vertice destino , String cor) 
	{
		Aresta e = new Aresta(origem, destino,cor);
		origem.addAdj(e);
		arestas.add(e);
	}

	private void AddAresta(Aresta toAdd)
	{
		toAdd.getOrigem().getAdj().add(toAdd);
		arestas.add(toAdd);
	}

	public void removeAresta(Aresta toRemove)
	{
		toRemove.getOrigem().getAdj().remove(toRemove);
		arestas.remove(toRemove);		
	}
	/*public void removerAresta(Vertice Ori , Vertice Dest)
	{	
		for (Aresta e : arestas) {
			if(e.ending.equals(Dest) && e.starting.equals(Ori))
			{	removeVertice(Dest.getNome());
				removeVertice(Ori.getNome());
				System.out.println(Dest.getNome() + Ori.getNome() );
				arestas.remove(e);
				break;
			}
		}


	}*/

	public void removeVertice(String min , String max)
	{	Iterator<Vertice>iter = vertices.iterator();
	Iterator<Aresta>iter2 = arestas.iterator();

	while(iter.hasNext()){
		Vertice v = iter.next();
		if(v.isIn(min, max)){
			v.getArestas().clear();
			iter.remove();

		}


	}}






	public String toString() {
		String r = "";
		System.out.println("Graph:");
		for (Vertice u :vertices) {
			r += u.getNome() + " -> ";
			for (Aresta e : u.getAdj()) {
				Vertice v = e.getDestino();
				r += v.getNome() + ", ";
			}
			r += "\n";
		}
		return r;
	}

	public void printGraph(String ext)
	{
		File graphFile = new File("graph_"+ext);
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new FileWriter(graphFile));

			String lineL="";
			String lineC="";
			for(int y=dimY-1;y>=0;y--)
			{
				lineC+="  ";
				for(int x=0;x<dimX;x++)
				{
					String sX="";
					String sY="";
					sX = x<10?"0"+x:""+x;
					sY = y<10?"0"+y:""+y;

					lineL+=""+sX+sY;
					if(contem(""+(x+1)+"."+y) && (getVertice(""+x+"."+y).getAresta(getVertice(""+(x+1)+"."+y))!=null))
						lineL+="-";
					else lineL+=" ";

					if(contem(""+x+"."+(y-1)) && (getVertice(""+x+"."+y).getAresta(getVertice(""+x+"."+(y-1)))!=null))
						lineC+="|    ";					
					else lineC+="     ";
				}	
				output.write(lineL+"\n");
				output.write(lineC+"\n");
				lineL="";
				lineC="";
			}

			output.close();

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}


	}

	public int dimX() {
		return dimX;
	}

	public int dimY() {
		return dimY;
	}

	public int indexOf(Vertice v) {
		return indexOf(v.getNome());
	}

	private int indexOf(String xy) {
		int x = Integer.parseInt(xy.split("\\.")[0]);
		int y = Integer.parseInt(xy.split("\\.")[1]);
		return x + y*this.dimX();
	}


	public  Graph(int dx , int dy , int k)
	{
		vertices = new ArrayList<>();
		arestas = new ArrayList<>();
		int Nvertices = k;
		dimX = dx;
		dimY = dy;


		//Adiciona Vertices
		for(int x=0; x<dimX; x++){
			for(int y=0; y<dimY; y++){
				Vertice sw = new Vertice (x +"." + y);
				addVertice(x+"."+ y);}

		}

		//Adiciona Arestas
		for(int y=0; y<dimY; y++)
			for(int x=0; x<dimX; x++){
				Vertice sw = new Vertice (x +"." + y);
				int i = 0;

				if(contem(x+"."+(y+1)) && contem(x+"." + (y)) && i < k){
					i++;
					addAresta(getVertice(x+"."+(y)), getVertice(x+"."+(y+1)));
				}
				if(contem(x+"."+((y-1)))  && contem(x+"." + (y)) && i < k){
					i++;
					addAresta(getVertice(x+"."+(y)), getVertice(x+"."+(y-1)));
				}

				if(contem((x+1)+"."+y )&& contem((x)+"." + y)&& i < k){
					i++;
					addAresta(getVertice((x)+"."+(y)), getVertice((x+1)+"."+y));	
				}

				if(contem((x-1)+"."+y)&& contem((x)+"." + y)&& i < k){
					i++;
					addAresta(getVertice((x)+"."+y), getVertice((x-1)+"."+y));	
				}

			}






	}





	/*public ArrayList<Vertice> Djkstra ( Graph grafo , Vertice fonte,Vertice fim){
	fonte.dist = 0;
	ArrayList<Vertice>Caminho = new ArrayList();

	if (fonte.equals(fim)){


	}


	for (Vertice v : grafo.getVertices()){
		if(AreNeighbours(fonte , v));{
		fonte.getAdj().remove(fonte);
		vertices.remove(v);
		Caminho.add(v);

	}}

return Caminho;


	}
	 */

	public static boolean AreNeighbours(String u, String v) {
		boolean areNeighbours = false;

		int x1 = Integer.parseInt(u.split("\\.")[0]);
		int y1 = Integer.parseInt(u.split("\\.")[1]);
		int x2 = Integer.parseInt(v.split("\\.")[0]);
		int y2 = Integer.parseInt(v.split("\\.")[1]);

		if (x2 == x1){
			if (y2 == y1){
				System.out.println("São o mesmo vertice!!");
				areNeighbours = false;
			}
		}

		if(x1 == x2+1) {
			if (y1 == y2)
				areNeighbours = true;
		}

		if (x2 == x1+1){
			if (y2 == y1){
				areNeighbours = true;
			}
		}
		if (x2 == x1){
			if (y2 == y1+1){
				areNeighbours = true;
			}
		}
		if (x2 == x1){
			if (y1 == y2+1){
				areNeighbours = true;
			}
		}
		if (x2 == x1){
			if (y2 == y1-1){
				areNeighbours = true;
			}
		}

		if (x2 == x1){
			if (y1 == y2-1){
				areNeighbours = true;
			}
		}
		if(x1 == x2-1) {
			if (y1 == y2)
				areNeighbours = true;
		}

		if(x2 == x1-1) {
			if (y1 == y2)
				areNeighbours = true;
		}

		return areNeighbours;
	}




	public int size() {
		int i = (dimX) * (dimY);
		return i;
	}





	/*public int[] neighbors(int next) {
int[] i = new int[100];
int n = 0;
	for(int j=0; j<=dimY + dimX; j++)
		i[j] = 0;

	for(int y=0; y<dimY; y++)
		for(int x=0; x<dimX; x++)
		{n++;
			if(contem(x+"."+(y+1)))
				addAresta(getVertice(x+"."+y), getVertice(x+"."+(y+1)), ports[0]); i[n]++;
			if(contem(x+"."+(y-1)))
				addAresta(getVertice(x+"."+y), getVertice(x+"."+(y-1)), ports[1]); i[n]++;
			if(contem((x+1)+"."+y)) 
				addAresta(getVertice(x+"."+y), getVertice((x+1)+"."+y), ports[2]); i[n]++;	
			if(contem((x-1)+"."+y)) 
				addAresta(getVertice(x+"."+y), getVertice((x-1)+"."+y), ports[3]);	i[n]++;


		}				


	return i;
}
	 */
	public boolean getAresta1(String nomeVerticeOri , String nomeVerticeDest ) {
		Aresta aresta = null;
		boolean b = false;

		for (Aresta a : this.arestas) {
			if (a.starting.getNome().equals(nomeVerticeOri)&& a.ending.getNome().equals(nomeVerticeDest))
				aresta = a;
			b = true;
		}

		if (aresta == null) {
			System.out.println("Aresta: " + nomeVerticeOri + "---to---"+ nomeVerticeDest + " nao encontrado");
			b = false;
		}
		return b;
	}
	public boolean getVertice1(String nomeVertice) {
		Vertice vertice = null;

		for (Vertice v : this.vertices) {
			if (v.getNome().equals(nomeVertice))
				vertice = v;
		}

		if (vertice == null) {
			System.out.println("Vertice: " + nomeVertice + " nao encontrado" );
			return false;
		}

		return true;
	}



	public ArrayList<Vertice> reduçãoCaminho (Vertice fim , Vertice Inicio)
	{	
		int i = 0;
		ArrayList<Vertice>caminho = new ArrayList<Vertice>();
		caminho.add(Inicio);
		int x1 = Integer.parseInt(Inicio.getNome().split("\\.")[0]);
		int y1 = Integer.parseInt(Inicio.getNome().split("\\.")[1]);
		int x2 = Integer.parseInt(fim.getNome().split("\\.")[0]);
		int y2 = Integer.parseInt(fim.getNome().split("\\.")[1]);
		if(y2 != 0){
			if((y1 == y2)){
				if(getAresta1(x1+"."+y1,(x1)+"."+(y1-1))){
					caminho.add(getVertice(x1+"."+(y1-1)));
					y1--;
				}
			}}else{
				if((y1 == y2)){
					if(getAresta1(x1+"."+y1,(x1)+"."+(y1+1))){
						caminho.add(getVertice(x1+"."+(y1+1)));
						y1++;
					}

				}}



		while ( !((x1 == x2) && (y1 == y2))  ){
			i++;
			if(i == 15){
				ArrayList<Vertice>caminhoaux = new ArrayList<Vertice>();
				Vertice v = new Vertice(x1+"."+y1);
				System.out.println(x1+"AQUIIIIIIIII KRALHO");
				caminhoaux = reduçãoCaminho1(fim,v);
				for(int k = 0 ; k < caminhoaux.size(); k++)
				{
					caminho.add(caminhoaux.get(k));
				}

				return caminho;
			}
			while(x2 > x1)
			{	

				if(getAresta1(x1+"."+y1,(x1+1)+"."+y1) && getVertice1((x1+1)+"."+(y1))){
					caminho.add(getVertice((x1+1)+"."+(y1)));
					x1++;
				}else break;

			}
			while(x1 >  x2)
			{

				/* if( AreNeighboursTop(Inicio,fim) == true )
		{
			caminho.add(fim);
		}
				 */
				if(getAresta1(x1+"."+y1,(x1-1)+"."+y1)&& getVertice1((x1-1)+"."+(y1))){
					caminho.add(getVertice((x1-1)+"."+(y1)));
					x1--;
				}else break;

			}


			while(y2 > y1)
			{	

				//	 if( AreNeighboursTop(Inicio,fim) == true )
				//{
				//	caminho.add(fim);
				//	}
				if(getAresta1(x1+"."+y1,x1+"."+(y1+1)) && getVertice1((x1)+"."+(y1+1))){
					caminho.add(getVertice((x1)+"."+(y1+1)));
					y1++;
				}else break;
			}
			while(y1 >  y2)
			{	

				/* if( AreNeighboursTop(Inicio,fim) == true )
		{
			caminho.add(fim);
		}
				 */
				if(getAresta1(x1+"."+y1,x1+"."+(y1-1))&& getVertice1((x1)+"."+(y1-1))){
					caminho.add(getVertice((x1)+"."+(y1-1)));
					y1--;
				}
				else break;

			}
			System.out.println(x1+"."+x2+"."+y1+"."+y2+"<--------------------------------");

		}

		return caminho;
	}




	public  boolean AreNeighboursTop(Vertice u, Vertice v) {
		boolean Neighbours = false;
		try{
			int x1 = Integer.parseInt(u.getNome().split("\\.")[0]);
			int y1 = Integer.parseInt(u.getNome().split("\\.")[1]);
			int x2 = Integer.parseInt(v.getNome().split("\\.")[0]);
			int y2 = Integer.parseInt(v.getNome().split("\\.")[1]);
			if(contem(x1+"."+(y1+1)) == true ){
				if (y1 == y2-1)
					Neighbours = true;
			}}catch(NullPointerException e){}
		return Neighbours;
	}
	public  boolean AreNeighboursBot(Vertice u, Vertice v) {
		boolean Neighbours = false;
		try{
			if(contem(Integer.parseInt(v.getNome().split("\\.")[0])+ "." +
					Integer.parseInt(v.getNome().split("\\.")[1])) == true )
			{
				int x1 = Integer.parseInt(u.getNome().split("\\.")[0]);
				int y1 = Integer.parseInt(u.getNome().split("\\.")[1]);
				int x2 = Integer.parseInt(v.getNome().split("\\.")[0]);
				int y2 = Integer.parseInt(v.getNome().split("\\.")[1]);
				if (y1 == y2-1);
				Neighbours = true;
			}}catch(NullPointerException e){}
		return Neighbours;
	}

	public  boolean AreNeighboursLeft(Vertice u, Vertice v) {
		boolean Neighbours = false;
		try{
			if(contem(Integer.parseInt(v.getNome().split("\\.")[0])+ "." +
					Integer.parseInt(v.getNome().split("\\.")[1])) == true )
			{
				int x1 = Integer.parseInt(u.getNome().split("\\.")[0]);
				int y1 = Integer.parseInt(u.getNome().split("\\.")[1]);
				int x2 = Integer.parseInt(v.getNome().split("\\.")[0]);
				int y2 = Integer.parseInt(v.getNome().split("\\.")[1]);
				if (x1 == x2-1)
					Neighbours = true;
			}}catch(NullPointerException e){System.out.println("OK!!");}
		return Neighbours;
	}

	public  boolean AreNeighboursRight(Vertice u, Vertice v) {

		boolean Neighbours = false;

		try{
			if(contem(Integer.parseInt(v.getNome().split("\\.")[0])+ "." +
					Integer.parseInt(v.getNome().split("\\.")[1])) == true )
			{
				int x1 = Integer.parseInt(u.getNome().split("\\.")[0]);
				int y1 = Integer.parseInt(u.getNome().split("\\.")[1]);
				int x2 = Integer.parseInt(v.getNome().split("\\.")[0]);
				int y2 = Integer.parseInt(v.getNome().split("\\.")[1]);

				if (x1 == x2+1)
					System.out.println("TRUE");
				Neighbours = true;
			}

		}catch(NullPointerException e){ System.out.println("Ok!");}
		return Neighbours;
	}


	/*public ArrayList<Vertice> reduçãoCaminho1 (Vertice fim , Vertice Inicio)
{	
	ArrayList<Vertice>caminho = new ArrayList<Vertice>();
	int x1 = Integer.parseInt(Inicio.getNome().split("\\.")[0]);
	int y1 = Integer.parseInt(Inicio.getNome().split("\\.")[1]);
	int x2 = Integer.parseInt(fim.getNome().split("\\.")[0]);
	int y2 = Integer.parseInt(fim.getNome().split("\\.")[1]);
	for(Vertice v : vertices){

	ArrayList<Aresta> links = suitableLinks(v);
	Aresta ln = getNextLink(links);
	links.remove(ln);
	Aresta nl = ln.getDestino().getAresta(ln.getOrigem());
	if (debug) System.err.println("Link now: "+ln.getOrigem().getNome()+" <-> "+ln.getDestino().getNome());
	caminho.add(v);
	}}




	caminho.add(fim);
	return caminho;
		}

protected Aresta getNextLink(ArrayList<Aresta> links) {
	Aresta got = null;
	int index, count = 0;
//	System.out.println(" \n 1->"+RRIndex[1] +" \n 2->"+ RRIndex [2]);

	if(RRIndex[1] == -1){
		if ((RRIndex[2] == -1)){ // first choice of this computation
				index = 0;
			}
		else{
			index = (RRIndex[2] + 1) %4;
		}

		}

	else { // other choices


		index = (RRIndex[1] + 1) % 4;
		if ((index - RRIndex[2]) % 2 == 0) {
			index = (index + 2) % 4;
		}
	}

	while (true) {
		for (Aresta ln : links) {
			if (ln.getCor() == RoundRobin[index]) {
				//System.out.println(RoundRobin[index]);
				got = ln;
				break;
			}
		}
		if (got != null)
			break;
		else {
			if (RRIndex[2] == ((RRIndex[1] + 1) % 4))
				index = (index + 5) % 4;
			else
				index = (index + 1) % 4;
		}
	}
	// updates the last turn
		RRIndex[0] = RRIndex[1];
		RRIndex[1] = RRIndex[2];
		RRIndex[2]= index;

	if(RRIndex[2] == 0)
	{
		count++;
	}
	if(RRIndex[2] == 3)
	{
		count--;
	}
	return got;
}


public ArrayList<Aresta> suitableLinks(Vertice v) 
{
	ArrayList<Aresta> adj = v.getAdj(); 
	if(adj.isEmpty())
		return null;

	ArrayList<Aresta> slinks = new ArrayList<>();
	for(Aresta ln : adj) {
		Vertice dst = ln.getDestino();


	return (slinks.isEmpty())? null : slinks;


}
	return slinks;
}



	 */

	public ArrayList<Vertice> reduçãoCaminho1 (Vertice fim , Vertice Inicio)
	{	int i = 0;
	ArrayList<Vertice>caminho = new ArrayList<Vertice>();
	int x1 = Integer.parseInt(Inicio.getNome().split("\\.")[0]);
	int y1 = Integer.parseInt(Inicio.getNome().split("\\.")[1]);
	int x2 = Integer.parseInt(fim.getNome().split("\\.")[0]);
	int y2 = Integer.parseInt(fim.getNome().split("\\.")[1]);
	caminho.add(Inicio);
	if(x1 != 0){
		if((x1 == x2)){
			if(getAresta1(x1+"."+y1,(x1-1)+"."+(y1))){
				caminho.add(getVertice((x1-1)+"."+(y1)));
				x1--;
			}
		}}
		if(x1 == 0){
			if((x1 == x2)){
				if(getAresta1(x1+"."+y1,(x1+1)+"."+(y1))){
					caminho.add(getVertice((x1+1)+"."+(y1)));
					x1++;
					System.out.println("OPAAAAAAAAAAAAAAAAAAAAAAAA");
				}
			}

		}
	while ( !((x1 == x2) && (y1 == y2))){

		i++;
		System.out.println(i);
		if(i == 15){
			ArrayList<Vertice>caminhoComp = new ArrayList<Vertice>();
			Vertice v = new Vertice(x1+"."+y1);
			System.out.println(x1+"."+y1);
			caminhoComp = reduçãoCaminho(fim,v);
			for(int k = 0 ; k < caminhoComp.size() ; k++)
			{
				caminho.add(caminhoComp.get(k));
			}
			return caminho;
		}

		while(y2 > y1)
		{

			//	 if( AreNeighboursTop(Inicio,fim) == true )
			//{
			//	caminho.add(fim);
			//	}
			if(getAresta1(x1+"."+y1,x1+"."+(y1+1))&& getVertice1((x1)+"."+(y1+1))){
				caminho.add(getVertice((x1)+"."+(y1+1)));
				y1++;
			}else break;
		}
		while(y1 >  y2)
		{

			/* if( AreNeighboursTop(Inicio,fim) == true )
		{
			caminho.add(fim);
		}
			 */
			if(getAresta1(x1+"."+y1,x1+"."+(y1-1))&&getVertice1((x1)+"."+(y1-1))){
				caminho.add(getVertice((x1)+"."+(y1-1)));
				y1--;
			}else break;
		}
		while(x2 > x1)
		{


			if(getAresta1(x1+"."+y1,(x1+1)+"."+y1)&& getVertice1((x1+1)+"."+(y1))){
				caminho.add(getVertice((x1+1)+"."+(y1)));
				x1++;
			}else break;

		}
		while(x1 >  x2)
		{

			/* if( AreNeighboursTop(Inicio,fim) == true )
			{
				caminho.add(fim);
			}
			 */
			if(getAresta1(x1+"."+y1,(x1-1)+"."+y1)&&getVertice1((x1-1)+"."+(y1))){
				caminho.add(getVertice((x1-1)+"."+(y1)));
				x1--;
			}else break;

		}



	}
	return caminho;

	}}



















