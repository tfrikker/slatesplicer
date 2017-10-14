import java.io.*;

public class ReadExample {
	public static void main(String[] args) {
		try {
			// Open the wav file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(new File(args[0]));

			// Display information about the wav file
			wavFile.display();

			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			int totalFramesRead = 0;
			double max = Double.MIN_VALUE;

			do {
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100); 
				// Loop through frames and look maximum value
				for (int s=0 ; s<framesRead * numChannels ; s++) {
					// only declare new max if at least 1.5x last one
					if (buffer[s] >= (1.5 * max)) {
						double seconds = (double) ((s + totalFramesRead) / (double) wavFile.getSampleRate());
						System.out.printf("New max of amplitude %f found at %f sec (frame: %d)\n", buffer[s], seconds, (s + totalFramesRead));
						max = buffer[s];
					}	
				}
				totalFramesRead += framesRead;
			}
			while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			// Output the minimum and maximum value
			System.out.printf("Max: %f\n", max);
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
