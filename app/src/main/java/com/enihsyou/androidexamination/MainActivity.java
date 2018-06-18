package com.enihsyou.androidexamination;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.enihsyou.androidexamination.domain.BookItem;
import com.enihsyou.androidexamination.domain.MovieItem;
import com.enihsyou.androidexamination.domain.MovieSearchResult;
import com.enihsyou.androidexamination.fragment.BookFragment;
import com.enihsyou.androidexamination.fragment.MovieFragment;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;
import com.enihsyou.androidexamination.network.VolleyNetworking;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    MovieFragment.OnMovieListFragmentInteractionListener,
    BookFragment.OnBookListFragmentInteractionListener {

    public static final int REQ_MOVIE_DETAIL = 0x1001;

    private Networking networking;

    private MenuItem settingAction;

    private MovieSearchResult doubanInTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.networking = new VolleyNetworking(this);

        // 侧边栏处理
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateFromDouban();
    }


    private void populateFromDouban() {
        this.networking.doubanInTheater(new SuccessCallback<MovieSearchResult>() {
            @Override
            public void onResponse(MovieSearchResult response) {
                MainActivity.this.doubanInTheaters = response;
            }
        }, null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        this.settingAction = menu.findItem(R.id.action_settings);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 在这里处理右上角菜单栏的点击
        if (id == R.id.action_settings) {
            Snackbar.make(findViewById(R.id.drawer_layout), "仅供娱乐", Snackbar.LENGTH_SHORT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 在这里处理侧边栏的点击
        final int id = item.getItemId();

        switch (id) {
            case R.id.nav_movie: {
                // 处理点击侧边栏 电影按钮 的逻辑
                final MovieFragment fragment = MovieFragment.newInstance(this.doubanInTheaters);

                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();

                break;
            }
            case R.id.nav_book: {
                // 处理点击侧边栏 图书按钮 的逻辑
                //Fragment fragment = BookFragment.newInstance(1);
                //
                //getSupportFragmentManager().beginTransaction()
                //    .replace(R.id.main_fragment_container, fragment)
                //    .commit();
                Toast.makeText(this, "图书部分未实现", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_setting:
                // 处理点击侧边栏 设置按钮 的逻辑
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        findViewById(R.id.usage).setVisibility(View.GONE);
        return true;
    }

    @Override
    public void onBookItemSelected(BookItem item) {
        System.out.println(item);
    }

    /** 点击了电影图标，在这里打开详情Activity */
    @Override
    public void onMovieItemSelected(MovieItem item) {
        final Intent intent = MovieDetailActivity.newIntent(this, item);
        startActivityForResult(intent, REQ_MOVIE_DETAIL);
    }

    /**通过点击海报 从详情Activity返回，显示提示*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Resources resources = getResources();
        switch (requestCode) {
            case REQ_MOVIE_DETAIL:
                if (resultCode == RESULT_OK) {
                    String string = resources.getString(R.string.select_movie_detail,
                        data.getStringExtra(MovieDetailActivity.ACK_MOVIE_DETAIL));
                    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
                }
        }
    }
}
