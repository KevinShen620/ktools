/*
 * 2019年3月6日 
 */
package kevsn.ktools.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author Kevin
 *
 */
public class SearchHideFiles {

	public static final Logger logger = Logger.getLogger("searchHideFiles");

	public void search(Path dir, Consumer<Path> resultHandler)
			throws IOException {
		if (!Files.exists(dir)) {
			throw new IllegalArgumentException("目录不存在" + dir);
		}
		if (!Files.isDirectory(dir)) {
			throw new IllegalArgumentException(dir + "不是目录");
		}
		Files.walk(dir).filter(p -> p.getFileName().toString().startsWith("."))
				.forEach(resultHandler);
	}

	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		Path path = Paths.get(filePath);
		new SearchHideFiles().search(path, p -> logger.info(p.toString()));
	}
}
