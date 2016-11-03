package classExamples;

import java.util.Random;

public class RandomProteinGenerator
{
	/* constructor 
	 *  
	 *  if useUniformFrequencies == true, the random proteins have an equal probability of all 20 residues.
	 *  
	 *  if useUniformFrequencies == false, the 20 residues defined by
	 *  { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y' }
	 *  
	 *  have a distribution of 
	 *  
	 *  {0.072658f, 0.024692f, 0.050007f, 0.061087f,
        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
        0.032955f}
	 * 
	 */
	private char[] proteins;
	private float[] distribution;
	
	public RandomProteinGenerator(boolean useUniformFrequencies)
	{
		proteins = new char[20];
		char[] letters = {'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y'};
		for (int i=0; i < 20; i++)
		{
			proteins[i] = letters[i];
		}
		if (useUniformFrequencies == true)
		{
			distribution = new float[20];
			for (int i=0; i < 20; i++)
			{
				distribution[i] = 0.05f;
			}
		}
		else
		{
			distribution = new float[20];
			float[] numbers = {0.072658f, 0.024692f, 0.050007f, 0.061087f,
			        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
			        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
			        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
			        0.032955f};
			for (int i=0; i < 20; i++)
			{
				distribution[i] = numbers[i];
			}
		}
	}
	
	
	/*
	 * Returns a randomly generated protein of length length.
	 */
	public String getRandomProtein(int length)
	{
		String randProt = "";
		//int rnd = new Random().nextInt(length);
		Random random = new Random();
		for(int i=0; i < length; i++)
		{
			int num = random.nextInt(20);
			//System.out.println(num);
			randProt = randProt + proteins[num];
			//System.out.println(randProt);
		}
		return randProt;
	}
	
	/*
	 * Returns the probability of seeing the given sequence
	 * given the underlying residue frequencies represented by
	 * this class.  For example, if useUniformFrequencies==false in 
	 * constructor, the probability of "AC" would be 0.072658 *  0.024692
	 */
	public double getExpectedFrequency(String protSeq)
	{
		double freq = 1;
		for (int i=0; i < protSeq.length(); i++)
			for (int j=0; j < proteins.length; j++)
			{
				if(proteins[j] == protSeq.charAt(i))
				{
					//System.out.println(distribution[j]);
					freq = freq*distribution[j];
				}
			}
		return freq;
	}
	
	/*
	* calls getRandomProtein() numIterations times generating a protein with length equal to protSeq.length().
	 * Returns the number of time protSeq was observed / numIterations
	 */
	public double getFrequencyFromSimulation (String protSeq, int numIterations)
	{
		double ratio;
		String protein;
		double obs = 0;
		for (int i=0; i < numIterations; i++)
		{
			protein = getRandomProtein(protSeq.length());
			if(protein.equals(protSeq))
			{
				obs = obs + 1;
			}
		}
		ratio = obs / numIterations;
		return ratio;
	}
	
	public static void main(String[] args) throws Exception
	{
		RandomProteinGenerator uniformGen = new RandomProteinGenerator(true);
		String testProtein = "ACD";
		int numIterations =  10000000;
		System.out.println(uniformGen.getExpectedFrequency(testProtein));  // should be 0.05^3 = 0.000125
		System.out.println(uniformGen.getFrequencyFromSimulation(testProtein,numIterations));  // should be close
		System.out.println("**********************");
		
		RandomProteinGenerator realisticGen = new RandomProteinGenerator(false);
		
		// should be 0.072658 *  0.024692 * 0.050007 == 8.97161E-05
		System.out.println(realisticGen.getExpectedFrequency(testProtein));  
		System.out.println(realisticGen.getFrequencyFromSimulation(testProtein,numIterations));  // should be close
		
	}
}
