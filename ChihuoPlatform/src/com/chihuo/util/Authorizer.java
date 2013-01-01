package com.chihuo.util;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;

public class Authorizer implements SecurityContext {

	private Principal principal = null;

	private UriInfo uriInfo;
	private User user;
	private Waiter waiter;

	public Authorizer(final User user, UriInfo uriInfo) {
		this.user = user;
		this.uriInfo = uriInfo;

		if (user != null) {
			principal = new Principal() {
				public String getName() {
					return "USER:" + user.getId();
				}
			};
		}
	}

	public Authorizer(final Waiter waiter, UriInfo uriInfo) {
		this.waiter = waiter;
		this.uriInfo = uriInfo;

		if (waiter != null) {
			principal = new Principal() {
				public String getName() {
					return "WAITER:" + waiter.getId();
				}
			};
		}
	}

	public Principal getUserPrincipal() {
		return principal;
	}

	/**
	 * @param role
	 *            Role to be checked
	 */
	public boolean isUserInRole(String role) {
		String[] roles = StringUtils.split(role, ",");
		List<String> list = java.util.Arrays.asList(roles);

		if (user != null) {
			if (user.getUtype() == 1 && list.contains("USER")) {
				return true;
			} else if (user.getUtype() == 2 && list.contains("OWER")) {
				return true;
			}
		}

		if (waiter != null && list.contains("WAITER")) {
			return true;
		}
		return false;
	}

	public boolean isSecure() {
		return "https".equals(uriInfo.getRequestUri().getScheme());
	}

	public String getAuthenticationScheme() {
		if (principal == null) {
			return null;
		}
		return SecurityContext.FORM_AUTH;
	}

}