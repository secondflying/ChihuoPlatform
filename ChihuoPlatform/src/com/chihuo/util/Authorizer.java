package com.chihuo.util;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Role;
import com.chihuo.bussiness.User;

public class Authorizer implements SecurityContext {

	private Principal principal = null;

	private UriInfo uriInfo;
	private User user;

	public Authorizer(final User user, UriInfo uriInfo) {
		this.user = user;
		this.uriInfo = uriInfo;

		if (user != null) {
			principal = new Principal() {
				public String getName() {
					return user.getId() + "";
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
		if (role.equals("USER")) {
			if (user != null) {
				return true;
			}
		} else {
			if (user != null) {
				for (Role r : user.getRoles()) {
					if (r.getName().equals(role)) {
						return true;
					}
				}
			}
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