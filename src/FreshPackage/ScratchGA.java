package FreshPackage;

import java.util.*;

class ScratchGA
{
	static Random rand = new Random();
	static int chromoLength = 5;
	static int genesLength = 4;
	static final int poolSize = 16;
	static double crossRate = .5;
	static double mutationRate = .001;
	static ArrayList pool = new ArrayList(poolSize);
	//static Chromosome chosenBest;
	//static double[] ratings = new double[5];

	public ScratchGA()
	{
		for(int x=1;x<=poolSize;x++)
			pool.add(new Chromosome());
	}

	public void generate(int img_no, double[] ratings)
	{
		Chromosome chosenBest = (Chromosome)pool.get(img_no);
		ArrayList newPool = new ArrayList(pool.size());
		System.out.println("Initial population : ");

		for(int x=0;x<poolSize;x++){
			Chromosome c = (Chromosome)pool.get(x);
			c.decodeChromo();
			c.fitnessScore(chosenBest,ratings);
			//System.out.println(c.chromo);
			
			for(int y=0;y<chromoLength;y++){
				String genes = c.decodedChromo[y];	
				System.out.print(genes + " ");
			}
				
			System.out.println();
		}
	
		for(int x=0;x<poolSize;x+=2)
		{
			//random selection of two chromosome (Roulette Wheel selection)
			Chromosome c1 = selectChromo(pool);
			Chromosome c2 = selectChromo(pool);

			//crossover and mutate
			c1.crossOver(c2);
			c1.mutate();
			c2.mutate();

			//rescore the chromosome
			
			c1.fitnessScore(chosenBest,ratings);
			c2.fitnessScore(chosenBest,ratings);

			newPool.add(c1);
			newPool.add(c2);
			pool.addAll(newPool);
		}

		System.out.println("\n\nAfter 1 generation : ");

		for(int x=0;x<poolSize;x++){
			Chromosome c = (Chromosome)pool.get(x);
			c.decodeChromo();
			//System.out.println(c.chromo);
			for(int y=0;y<chromoLength;y++){
				String genes = c.decodedChromo[y];	
				System.out.print(genes + " ");
			}
				
			System.out.println();
		}
	}



	public Chromosome selectChromo(ArrayList pool)
	{
		//Get the total fitness
		double tot=0.0;
		for(int x=pool.size()-1;x>=0;x--)
		{
			double score = ((Chromosome)pool.get(x)).score;
			tot += score;
		}
		double slice = tot*rand.nextDouble();
		//Loop to find the node
		double ttot = 0.0;
		for(int x=pool.size()-1;x>=0;x--)
		{
			Chromosome node = (Chromosome)pool.get(x);
			ttot += node.score;
			if(ttot >= slice){
				pool.remove(x);
				return node;
			}
		}
		return (Chromosome)pool.remove(pool.size()-1);
	}

	private static class Chromosome
	{
		public StringBuffer chromo = new StringBuffer(chromoLength);
		public String[] decodedChromo = new String[chromoLength];
		public double score;

		public Chromosome()
		{
			getRandomIndividual();
			score = 1.0;			
		}

		private void getRandomIndividual()
		{
			// Individual chromosome buffer
			for(int x=1;x<=chromoLength;x++)
			{
				String gene = Integer.toBinaryString(rand.nextInt(poolSize)); // create a random binary string 4 bits long
				
				// filling in the missing bits if the string is not 4 bits long (ex: for 2 it would be 10)
				int fillLeft = genesLength - gene.length();	
				for(int y=1;y<=fillLeft;y++)
					chromo.append('0');
				chromo.append(gene);
			}
			decodeChromo();
		}

		public void decodeChromo()
		{
			int index=0;
			for(int x=0;x<chromo.length();x+=4)
			{
				int gene_no = (Integer.parseInt(chromo.substring(x,x+4),2));
				String gene = String.valueOf(gene_no);
				decodedChromo[index++] = gene;
			}
		}

		public void crossOver(Chromosome adtha_Chromo)
		{
			if(rand.nextDouble() > crossRate) return;

			int crossOverPosition = rand.nextInt(chromo.length()); // selecting a random crossover position
			for(int x=crossOverPosition;x<chromo.length();x++)
			{
				char tmp = chromo.charAt(x);
				chromo.setCharAt(x,adtha_Chromo.chromo.charAt(x));
				adtha_Chromo.chromo.setCharAt(x,tmp);
			}	
		}

		public void mutate()
		{
			for(int x=0;x<chromo.length();x++)
			{
				if(rand.nextDouble() <= mutationRate)
					chromo.setCharAt(x,(chromo.charAt(x)=='0'?'1':'0'));
			}
		}

		public void fitnessScore(Chromosome chosenBest, double[] ratings)
		{
			chosenBest.decodeChromo();
			double c;
			double score = 0.0;
			for(int x=0;x<chromoLength;x++)
			{
				if(this.decodedChromo[x] == chosenBest.decodedChromo[x])
					c = 1.0;
				else
					c = 0.05;
				score += c * ratings[x];
			}
			this.score = score;
			
		}

	}

	public static void main(String args[])
	{
		ScratchGA img_pool = new ScratchGA();
		//img_pool.populationGenerator();
		int compoImg_no = img_pool.selectBest();
		double[] ratings = new double[chromoLength];
		ratings = img_pool.rateBest();
		img_pool.generate(compoImg_no,ratings);
	}

	public int selectBest()
	{
		//Chromosome chosenBest = (Chromosome)pool.get(x);
		return 3;
	}

	public double[] rateBest()
	{
		double[] ratings = {0.8,0.5,0.4,0.9,0.1};
		return ratings;
	}
}	