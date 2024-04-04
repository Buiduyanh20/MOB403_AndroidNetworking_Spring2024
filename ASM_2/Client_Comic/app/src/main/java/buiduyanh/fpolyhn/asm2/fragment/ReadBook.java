package buiduyanh.fpolyhn.asm2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import buiduyanh.fpolyhn.asm2.API.ApiService;
import buiduyanh.fpolyhn.asm2.MainActivity;
import buiduyanh.fpolyhn.asm2.R;
import buiduyanh.fpolyhn.asm2.RetrofitInstance;
import buiduyanh.fpolyhn.asm2.adapter.ReadBookAdapter;
import buiduyanh.fpolyhn.asm2.model.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadBook extends Fragment {
    private Toolbar toolbar_readbook;
    private String idGetBook;
    private List<String> listImage = new ArrayList<>();
    private RecyclerView recycler_readbook;
    private ReadBookAdapter readBookAdapter;
    private ApiService apiService;
    public ReadBook() {
        // Required empty public constructor
    }

    public static ReadBook newInstance(String idBook) {
        ReadBook fragment = new ReadBook();
        Bundle bundle = new Bundle();
        bundle.putString("idBook", idBook);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar_readbook = view.findViewById(R.id.toolbar_readbook);
        recycler_readbook = view.findViewById(R.id.recycler_readbook);
        getDataBundle();
        directToolBar();
        getDataBook();
        readBookAdapter = new ReadBookAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_readbook.setLayoutManager(layoutManager);
        recycler_readbook.setAdapter(readBookAdapter);
    }
    private void directToolBar(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setSupportActionBar(toolbar_readbook);
            toolbar_readbook.setNavigationOnClickListener(view1 -> {
                getActivity().onBackPressed();
            });
        }
    }
    private void getDataBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            idGetBook = bundle.getString("idBook");
        }
    }
    private void getDataBook(){
        apiService = RetrofitInstance.getApiService();
        Call<Book> call = apiService.getListImage(idGetBook, "true");
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                listImage = response.body().getImages();
                readBookAdapter.setDataReadBook(listImage);
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("TAGLISTIMAGE", "onFailure: Loi roi");
            }
        });
    }
}