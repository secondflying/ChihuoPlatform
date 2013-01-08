package com.chihuo.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.OrderItemDao;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.util.CodePlatform;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.DeviceRegister;
import com.chihuo.util.PublicHelper;

	@Path("/QR")
	public class QRCodeResource {
	
		public QRCodeResource() {
		}
		
		@Path("{qr}")
		@GET
		@Produces("application/json; charset=UTF-8")
		public Response joinOrder(@PathParam("qr") String qrcode,
				@Context HttpServletRequest request,
				@Context SecurityContext securityContext) {
			String[] tmp = qrcode.split("_");
			
			int rid = Integer.parseInt(tmp[0]);
			int deskid = Integer.parseInt(tmp[1]);
			
			RestaurantDao dao = new RestaurantDao();
			Restaurant r = dao.findById(rid);
			checkNull(r);
			
			OrderDao odao = new OrderDao();
			Order order = odao.findByDesk(r, deskid);
			if (order == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("编码错误")
						.type(MediaType.TEXT_PLAIN).build();
			}

			String udid = request.getHeader("X-device");
			if (!StringUtils.isBlank(udid)) {
				Device device  = DeviceRegister.register(udid,CodePlatform.Android);
				User u = PublicHelper.getLoginUser(securityContext);
				if(u != null){
					DeviceRegister.recordLogin(order, device, u.getId(), CodeUserType.USER);
				}else{
					DeviceRegister.recordLogin(order, device, -1, CodeUserType.ANONYMOUS);
				}
			}

			OrderItemDao oDao = new OrderItemDao();
			List<OrderItem> list = oDao.queryByOrder(order.getId());
			order.setOrderItems(list);

			return Response.status(Response.Status.OK).entity(order)
					.type(MediaType.APPLICATION_JSON).build();
		}
		
		private void checkNull(Restaurant c){
			if (c == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
			if(c.getStatus() == -1){
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
		}
	}
