/*
 * 2019年3月6日 
 */
package kevsn.ktools.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 * @author Kevin
 *
 */
public class ClearFiles {

	private static final Logger logger = Logger.getLogger("clearDirs");

	public void clear(Path dirPath, String... files) throws IOException {
		if (!Files.exists(dirPath)) {
			throw new IllegalArgumentException(dirPath + "存在");
		}
		if (!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException(dirPath + "不是目录");
		}
		Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (isTargetFile(file, files)) {
					logger.info("删除文件" + file);
					Files.delete(file);
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				if (isTargetFile(dir, files)) {
					FileUtils.deleteDirectory(dir.toFile());
					logger.info("删除目录" + dir);
					return FileVisitResult.SKIP_SUBTREE;
				}
				return FileVisitResult.CONTINUE;
			}
		});

	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("请指定目录和要清楚的文件");
		}
		String dir = args[0];
		Path dirPath = Paths.get(dir);
		String[] files = new String[args.length - 1];
		System.arraycopy(args, 1, files, 0, files.length);
		ClearFiles clear = new ClearFiles();
		clear.clear(dirPath, files);
	}

	private boolean isTargetFile(Path currentFile, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String fname = args[i];
			if (currentFile.getFileName().toString().equals(fname)) {
				return true;
			}
		}
		return false;
	}
}
