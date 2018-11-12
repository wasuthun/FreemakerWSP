import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

	public static void main(String[] args) throws IOException {

		AtomicInteger counter = new AtomicInteger(0);
		FileWriter fileWriter = new FileWriter(new File("./DependencyData.csv"));
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print("Instability,Abstractness\n");

		try {
			Files.walk(Paths.get("/Users/johnpaul/Desktop/Freemaker/freemarker/")).forEach(file -> {
				File dir = new File(file.toString());
				if (dir.isDirectory()) {
					FileRead fr = new FileRead();
					for (String myFile : dir.list()) {
						if (myFile.contains(".java")) {
							fr.readInFile(dir.toString(), myFile);
						}
					}
					System.out.println(counter.incrementAndGet());
					System.out.println(dir);

					PackageInfo pack = new PackageInfo(fr.getNa(), fr.getNc(), fr.getCa(), fr.getCe());

					if (!Double.isNaN(pack.getInstability()) && !Double.isNaN(pack.getAbstractness()))
						printWriter.printf("%.6f,%.6f \n", pack.getInstability(), pack.getAbstractness());

				}
			});
			System.out.println(counter.get() + " files has been read");
			printWriter.close();
			System.out.println("ada");
		} catch (Exception e) {
			return;
		}
	}

}