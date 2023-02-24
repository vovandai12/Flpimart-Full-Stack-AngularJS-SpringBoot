package com.example.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.StorageException;
import com.example.exception.StorageFileNotFoundException;
import com.example.service.StorageService;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
	private final Path rootLocation = Paths.get("uploads");

	@Override
	public String getStorageFilename(MultipartFile file, String id) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}

	@Override
	public void store(MultipartFile file, String storageFilename) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Không thể lưu trữ tệp trống " + storageFilename);
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(storageFilename)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Không thể lưu trữ tệp " + storageFilename + ". Đường dẫn không hợp lệ.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new StorageException("Không thể lưu trữ tệp " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Resource loadAsResource(String storageFilename) {
		try {
			Path file = load(storageFilename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("Không thể đọc tệp: " + storageFilename);
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Không thể đọc tệp: " + storageFilename, e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public void delete(String storageFilename) throws IOException {
		Path file = rootLocation.resolve(Paths.get(storageFilename)).normalize().toAbsolutePath();
		Files.delete(file);
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (IOException e) {
			throw new StorageException("Không thể khởi tạo thư mục để tải lên !", e);
		}
	}
}
