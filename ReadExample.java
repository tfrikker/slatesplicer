import java.io.*;

public class ReadExample
{
	public static void main(String[] args)
	{
		try
		{
			// Open the wav file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(new File(args[0]));

			// Display information about the wav file
			wavFile.display();

			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			double max = Double.MIN_VALUE;

			do
			{
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				// Loop through frames and look for minimum and maximum value
				for (int s=0 ; s<framesRead * numChannels ; s++)
				{
					if (buffer[s] > max) {
						System.out.printf("New max of amplitude %f found at frame: %d\n", buffer[s], s);
						max = buffer[s];
					}	
				}
			}
			while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			// Output the minimum and maximum value
			System.out.printf("Max: %f\n", max);
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
}
