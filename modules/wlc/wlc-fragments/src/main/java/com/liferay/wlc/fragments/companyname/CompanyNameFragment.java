package com.liferay.wlc.fragments.companyname;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import wlc.common.constants.WlcConstants;

/**
 * @author Andre Batista
 */

@Component(service = FragmentRenderer.class)
public class CompanyNameFragment implements FragmentRenderer{

	@Override
	public String getCollectionKey() {
		return WlcConstants.WLC_COLLECTION_KEY;
	}
	
	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "wlc-company");
	}

	@Override
	public void render(FragmentRendererContext fragmentRendererContext, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		
		try {
			
			_log.info("rendering company name");
			
			PrintWriter printWriter = httpServletResponse.getWriter();
			
			Principal companyPrincipal = httpServletRequest.getUserPrincipal();
			
			Company company = _companyLocalService.fetchCompany(GetterUtil.getLong(companyPrincipal.getName()));
			
			if (Objects.nonNull(company)) {
				company.getLegalName();
	
				printWriter.write(StringPool.COMMA);
				printWriter.write(StringPool.SPACE);
				printWriter.write(company.getLegalName());
				printWriter.write(StringPool.EXCLAMATION);
			
			}
		} catch (Exception e) {
			
			_log.error(e.getMessage());
			
		}
		
	}
	
	private static final Log _log = LogFactoryUtil.getLog(CompanyNameFragment.class);
	
	@Reference(target = "(bundle.symbolic.name=com.liferay.wlc.fragments)")
	private ResourceBundleLoader _resourceBundleLoader;
	
	@Reference
	private CompanyLocalService _companyLocalService;
}
