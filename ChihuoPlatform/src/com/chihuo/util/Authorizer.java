package com.chihuo.util;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Role;
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

		if (user != null) {
			principal = new Principal() {
				public String getName() {
					return "WAITER:" + user.getId();
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
		if (user != null && role.equals("USER")) {
			return true;
		}
		if (waiter != null && role.equals("WAITER")) {
			return true;
		}
		if (user != null) {
			for (Role r : user.getRoles()) {
				if (r.getName().equals(role)) {
					return true;
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