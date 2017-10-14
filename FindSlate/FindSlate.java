import java.io.*;

public class FindSlate {
	public static void main (String[] args) {
		try {
			// Open the wav file specified as the first argument
			WavFile input = WavFile.openWavFile(new File(args[0]));

			// Display information about the wav file
			input.display();

			// Get the number of audio channels in the wav file
			int numChannels = input.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			int totalFramesRead = 0;
			double max = Double.MIN_VALUE;
			// the sensitivity of the search
			double sensitivity = Double.parseDouble(args[1]);
			// the maximum range in seconds of the search from the start
			int range = Integer.parseInt(args[2]);

			do {
				// Read frames into buffer
				framesRead = input.readFrames(buffer, 100); 
				// Loop through frames and look maximum value
				for (int s = 0 ; s < framesRead * numChannels ; s++) {
					// only declare new max if at least sensitivity * last one
					if (buffer[s] >= (sensitivity * max)) {
						double seconds = (double) ((s + totalFramesRead) / (double) input.getSampleRate());
						System.out.printf("New max of amplitude %f found at %f sec (frame: %d)\n", buffer[s], seconds, (s + totalFramesRead));
						max = buffer[s];
					}	
				}
				totalFramesRead += framesRead;
			}
			while (framesRead != 0 && totalFramesRead < (input.getSampleRate() * range));

			// Close the wavFile
			input.close();

			// Output the maximum value
			System.out.printf("Max: %f\n", max);
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
