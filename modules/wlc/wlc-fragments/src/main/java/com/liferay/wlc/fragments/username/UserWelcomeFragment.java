package com.liferay.wlc.fragments.username;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.wlc.fragments.companyname.CompanyNameFragment;

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
public class UserWelcomeFragment implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return WlcConstants.WLC_COLLECTION_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle,"user-info");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {
		
		
		try {
			
			_log.info("rendering user name");
			
			PrintWriter printWriter = httpServletResponse.getWriter();
	
			Principal userPrincipal = httpServletRequest.getUserPrincipal();
	
			User user = _userLocalService.fetchUser(GetterUtil.getLong(userPrincipal.getName()));
	
			printWriter.write("<h1>Welcome");
	
			if (Objects.nonNull(user)) {
				user.getFirstName();
	
				printWriter.write(StringPool.COMMA);
				printWriter.write(StringPool.SPACE);
				printWriter.write(user.getFirstName());
				printWriter.write(StringPool.EXCLAMATION);
			}
	
			printWriter.write("</h1>");
		
		} catch (Exception e) {
			
			_log.error(e.getMessage());
		}
	}
	
	private static final Log _log = LogFactoryUtil.getLog(UserWelcomeFragment.class);
	
	@Reference
	private UserLocalService _userLocalService;
	
	@Reference(target = "(bundle.symbolic.name=com.liferay.wlc.fragments)")
	private ResourceBundleLoader _resourceBundleLoader;

}
