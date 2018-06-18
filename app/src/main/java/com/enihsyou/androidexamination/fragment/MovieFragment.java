package com.enihsyou.androidexamination.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.enihsyou.androidexamination.R;
import com.enihsyou.androidexamination.adapter.MovieSearchProvider;
import com.enihsyou.androidexamination.adapter.MyMovieRecyclerViewAdapter;
import com.enihsyou.androidexamination.dao.MovieSearchDatabaseHelper;
import com.enihsyou.androidexamination.domain.MovieItem;
import com.enihsyou.androidexamination.domain.MovieSearchResult;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;
import com.enihsyou.androidexamination.network.VolleyNetworking;

public class MovieFragment extends Fragment {

    public static final String TAG = MovieFragment.class.getSimpleName();

    private static final String ARG_MOVIE_ITEMS = "movie-items";

    private MovieSearchResult movieItems;

    private Networking networking;

    private OnMovieListFragmentInteractionListener interactionListener;

    private RecyclerView recyclerView;

    private SearchView actionSearch;

    private MovieSearchDatabaseHelper databaseHelper;

    public MovieFragment() {
    }

    /** 创建新的Fragment实例 */
    public static MovieFragment newInstance(MovieSearchResult movieItems) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();

        // put arguments
        args.putSerializable(ARG_MOVIE_ITEMS, movieItems);

        fragment.setArguments(args);
        return fragment;
    }

    /** 在Attach阶段，判断被挂载的父组件必须实现 {@link OnMovieListFragmentInteractionListener} */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieListFragmentInteractionListener) {
            this.interactionListener = (OnMovieListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnMovieListFragmentInteractionListener");
        }
    }

    /** 更新列表元素*/
    public void updateItems(MovieSearchResult movieItems) {
        this.movieItems = movieItems;
        final MyMovieRecyclerViewAdapter movieAdapter =
            new MyMovieRecyclerViewAdapter(this.movieItems, this.networking, this.interactionListener);
        this.recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 允许Fragment向应用栏添加项目
        setHasOptionsMenu(true);

        // 读取传递进来的参数
        final Bundle arguments = getArguments();
        if (arguments != null) {
            // read arguments
            this.movieItems = (MovieSearchResult) arguments.getSerializable(ARG_MOVIE_ITEMS);
        }

        this.networking = new VolleyNetworking(getContext());
        this.databaseHelper = new MovieSearchDatabaseHelper(getActivity());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.movie, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search_movie);
        final SearchView actionSearch = (SearchView) menuItem.getActionView();
        final SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        actionSearch.setSearchableInfo(searchManager.getSearchableInfo((getActivity().getComponentName())));

        actionSearch.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 处理搜索
                handleQueryTextSubmit(query);

                // 保存搜索记录
                saveQueryRecord(query);

                // 收起输入法，fixme 这句语句有Warning问题
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        this.actionSearch = actionSearch;
    }

    private void handleQueryTextSubmit(String query) {
        this.networking.searchMovie(query, new SuccessCallback<MovieSearchResult>() {
            @Override
            public void onResponse(MovieSearchResult response) {
                updateItems(response);
            }
        }, null);
    }

    private void saveQueryRecord(String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(),
            MovieSearchProvider.AUTHORITY, MovieSearchProvider.MODE);
        suggestions.saveRecentQuery(query, null);
        // 把记录插入到数据库中
        this.databaseHelper.insertRecord(query);

        final String msg = "搜索记录：" + String.valueOf(this.databaseHelper.getRecords());
        Log.i(TAG, msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            this.recyclerView = (RecyclerView) view;
            updateItems(this.movieItems);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.interactionListener = null;
    }


    public interface OnMovieListFragmentInteractionListener {

        void onMovieItemSelected(MovieItem item);
    }
}
