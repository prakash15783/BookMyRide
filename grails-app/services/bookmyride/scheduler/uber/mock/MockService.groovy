package bookmyride.scheduler.uber.mock;

import com.uber.sdk.rides.client.Callback;
import com.uber.sdk.rides.client.UberRidesAsyncService;
import com.uber.sdk.rides.client.model.PriceEstimatesResponse;
import com.uber.sdk.rides.client.model.Product;
import com.uber.sdk.rides.client.model.ProductsResponse;
import com.uber.sdk.rides.client.model.Promotion;
import com.uber.sdk.rides.client.model.Ride;
import com.uber.sdk.rides.client.model.RideEstimate;
import com.uber.sdk.rides.client.model.RideMap;
import com.uber.sdk.rides.client.model.RideRequestParameters;
import com.uber.sdk.rides.client.model.SandboxProductRequestParameters;
import com.uber.sdk.rides.client.model.SandboxRideRequestParameters;
import com.uber.sdk.rides.client.model.TimeEstimatesResponse;
import com.uber.sdk.rides.client.model.UserActivityPage;
import com.uber.sdk.rides.client.model.UserProfile;

class MockService implements UberRidesAsyncService{

		@Override
		public void requestRide(RideRequestParameters paramRideRequestParameters, Callback<Ride> paramCallback) {
			System.out.println("Processing param : "+paramRideRequestParameters)
			Ride ride = new Ride();
			ride.request_id = paramRideRequestParameters.getProductId()+paramRideRequestParameters.getStartLatitude()+"_processed"
			paramCallback.success(ride, null);
		}
				
		@Override
		public void getPromotions(float paramFloat1, float paramFloat2,
				float paramFloat3, float paramFloat4,
				Callback<Promotion> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getUserActivity(Integer paramInteger1,
				Integer paramInteger2, Callback<UserActivityPage> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getUserProfile(Callback<UserProfile> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getPriceEstimates(float paramFloat1, float paramFloat2,
				float paramFloat3, float paramFloat4,
				Callback<PriceEstimatesResponse> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getPickupTimeEstimates(float paramFloat1,
				float paramFloat2, String paramString,
				Callback<TimeEstimatesResponse> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getProducts(float paramFloat1, float paramFloat2,
				Callback<ProductsResponse> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getProduct(String paramString,
				Callback<Product> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cancelRide(String paramString, Callback<Void> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getRideDetails(String paramString,
				Callback<Ride> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void estimateRide(
				RideRequestParameters paramRideRequestParameters,
				Callback<RideEstimate> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getRideMap(String paramString,
				Callback<RideMap> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateSandboxProduct(
				String paramString,
				SandboxProductRequestParameters paramSandboxProductRequestParameters,
				Callback<Void> paramCallback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateSandboxRide(String paramString,
				SandboxRideRequestParameters paramSandboxRideRequestParameters,
				Callback<Void> paramCallback) {
			// TODO Auto-generated method stub
			
		}
	
	}