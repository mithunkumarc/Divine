package divine.calcify.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.ServicesFragment;
import divine.calcify.model.Partner;
import divine.calcify.model.PartnerProfileInfo;
import divine.calcify.webservices.DivineServicesWebService;

public class PartnerProfileInformationActvity extends AppCompatActivity {
    public static PartnerProfileInfo partnerProfileInfo;
    Partner partner;
    public TextView ppi_partner_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_profile_information_actvity);
        //change status bar to primary dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.ppi_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        partner = (Partner) getIntent().getSerializableExtra("partner_object");
        ppi_partner_info = (TextView)findViewById(R.id.ppi_partner_info);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.ppi_collapsing_toolbar);
        collapsingToolbar.setTitle(partner.getPartnerName()+"\n"+partner.getLocation());


        loadBackdrop();
        Double result;
        try{
            DivineServicesWebService divineServicesWebService = new DivineServicesWebService(this, DivineKeyWords.PARTNER_PROFILE_INFO,partner);
            result= divineServicesWebService.execute().get();
            if(result == 100.00){
                ppi_partner_info.setText(partnerProfileInfo.getPartnerProfile());
            }
        }catch (Exception e){

        }


    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.img_nature);
    }
    //////////asnc taskstart- allow activity to load new data basis of selected location
    /*private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(PartnerProfileInformationActvity.this);
        String typeStatus;


        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("waiting");
            //show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //don't touch dialog here it'll break the application
            //do some lengthy stuff like calling login webservice
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for(Fragment fragment : fragmentList){
                        if(fragment instanceof ServicesFragment){
                            ((ServicesFragment) fragment).initializeAdapter();
                        }
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide the dialog

            super.onPostExecute(result);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    asyncDialog.dismiss();
                    toolbarSearchDialog.dismiss();
                }
            });

        }

    }*/
    ////////////asnc task end
}
