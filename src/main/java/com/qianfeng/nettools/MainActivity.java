package com.qianfeng.nettools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ViewPager viewPager;
    ArrayList<ImageView> list=new ArrayList<>();

    int[] images={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    String[] urls={
            "http://xcb.chzu.edu.cn/picture/article/18/c2/18/7a7bd8604780a54291c474b4a227/05a27ccf-bb34-4c61-ad95-a6a91352bd2f.jpg",
            "http://xcb.chzu.edu.cn/picture/article/18/c2/18/7a7bd8604780a54291c474b4a227/a83219d2-3e78-4c39-a06c-310f9955ab7e.jpg",
            "http://xcb.chzu.edu.cn/picture/article/18/c2/18/7a7bd8604780a54291c474b4a227/8d1d74a4-f630-4e7c-a1f8-1d881c7cecb1.jpg",
            "http://xsc.chzu.edu.cn/picture/article/165/43/0c/898b9a4c41f3b66e4163fdcd6ab0/27c5fee4-b684-4886-836e-5e016461c49d.jpg",
            "http://xsc.chzu.edu.cn/picture/article/165/43/0c/898b9a4c41f3b66e4163fdcd6ab0/f2d5601d-ed71-4f14-97ca-12eb2295d9c4.jpg",
            "http://xcb.chzu.edu.cn/picture/article/18/c2/18/7a7bd8604780a54291c474b4a227/ef0c155c-e346-4050-b1f0-0d9ce62100ea.jpg"
    };
    Handler handler=new Handler();
    Runnable r;
    Myadapter myadapter;
    boolean flag=false;
    int click_count=0;
    private ImageView iv;
    private TextView text_header_name,text_email;
    String url="https://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager= (ViewPager) findViewById(R.id.viewpager);



        myadapter=new Myadapter();
        for (int i = 0; i < images.length; i++) {
            ImageView iv=new ImageView(this);
            iv.setImageResource(images[i]);
            list.add(iv);
            new Myasyntask().execute(urls[i], iv);
        }
        viewPager.setAdapter(myadapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.removeCallbacks(r);
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (flag) {
                            handler.postDelayed(r, 2000);
                            flag = false;
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:

                        break;

                    default:
                        break;
                }
            }
        });
        r=new Runnable() {

            @Override
            public void run() {

                int item=viewPager.getCurrentItem();
                item++;
                viewPager.setCurrentItem(item,true);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(r);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "闫乾文的毕业设计", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        iv= (ImageView) headerView.findViewById(R.id.imageView);
        text_header_name= (TextView)headerView. findViewById(R.id.text_header_name);
        text_email= (TextView) headerView.findViewById(R.id.textView_email);
             iv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                 }
             });
        Intent data=getIntent();
        String name = data.getStringExtra("name");
        String password = data.getStringExtra("password");
        Log.i("tag","---------------->"+name);
        Log.i("tag","---------------->"+password);
        text_header_name.setText(name);
        text_email.setText(password);
    }
    public void ping (View v){
        Intent intent=new Intent(this, PingActivity.class);
        startActivity(intent);

    }
    public void duankou(View v){
        Intent intent=new Intent(this, DuankouActivity.class);
        startActivity(intent);

    }
    public void baidu(View v){
        Intent intent=new Intent(this, WebViewActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }

    class Myadapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(list.get(position%list.size()));
            return list.get(position%list.size());
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(list.get(position%list.size()));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            click_count++;
            if (click_count <= 1) {
                Toast.makeText(this, "再次点击退出网络工具", Toast.LENGTH_SHORT).show();

            }else {
                super.onBackPressed();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    click_count = 0;
                }
            }, 1500);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Snackbar.make(drawer,"滁州学院",Snackbar.LENGTH_SHORT).show();

        } else if (id == R.id.nav_gallery) {
            Snackbar.make(drawer,"计算机与信息工程学院",Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Snackbar.make(drawer,"网络工程",Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Snackbar.make(drawer,"二班",Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(r);
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1) {

        }
    }
}
