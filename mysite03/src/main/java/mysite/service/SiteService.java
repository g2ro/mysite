package mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mysite.component.FileUploader;
import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;


@Service
public class SiteService {
	private SiteRepository siteRepository;
	private FileUploader fileUploader;
	public SiteService(SiteRepository siteRepository, FileUploader fileUploader) {
		this.siteRepository = siteRepository;
		this.fileUploader = fileUploader;
	}
	
	public SiteVo getSite() {
		return siteRepository.getSite();
	}

	public void updateSite(String title, String welcom, MultipartFile file1, String description) {
		String url = null;
		if(file1.getSize() == 0) {
			url = siteRepository.getSite().getProfile();
			siteRepository.updateSite(title, welcom, url, description);
			return;
		}
		url = fileUploader.restore(file1);
		siteRepository.updateSite(title, welcom, url, description);
	}
}
