package com.google.firebase.referencecode.projectlastterm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.referencecode.projectlastterm.R.id.amount;


public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuViewHolder> {

    ArrayList<listMenuModel> list;
    ListOrderModel listOrderModel;
    Context context;
    Button btnAddAmount, btnSubAmount;
    TextView amount;
     String nameTable;
    RecyclerView recyclerView;
    public ListMenuAdapter(){}
    public ListMenuAdapter(ArrayList<listMenuModel> list, Context context, String nameTable)
    {

        this.list=list;
        this.context=context;
        this.nameTable=nameTable;
    }
    public ListMenuAdapter(ArrayList<listMenuModel> list, Context context)
    {

        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public ListMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_listmenu, parent, false);

        recyclerView=view.findViewById(R.id.listOrder);
        btnAddAmount=view.findViewById(R.id.btnAddFood);
        btnSubAmount=view.findViewById(R.id.btnSubFood);
        return new ListMenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ListMenuViewHolder holder, int position) {
        listMenuModel current = list.get(position);

        holder.name.setText(current.getName());
        holder.price.setText(String.valueOf(current.getPrice()));
        Picasso.get().load(current.getUrl()).into(holder.imageView);

       // btnAddAmount.setId(position);
        //System.out.println(btnAddAmount.getId());
        //amount.setId(position);
       // System.out.println(amount.getId());

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.amount.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))+1));
            }
        });
        btnSubAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(String.valueOf(holder.amount.getText()))>1)
                holder.amount.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))-1));
            }
        });
       // System.out.println(txtNameTable.getText());

        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void onclick(View view, int position, boolean isLongClick) {
                if (isLongClick) {


                    String urlOrder="http://192.168.1.6/KTCK/nameFood.php?nameTable="+nameTable+"&nameFood="+current.getName();
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            //Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();
                            if (Integer.parseInt(response.trim())<1)
                            {
                                String urlOrder="http://192.168.1.6/KTCK/insertOrder.php";
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                                    @Override

                                    public void onResponse(String response) {
                                        Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();

//
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {

                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("nameTable", nameTable);
                                        params.put("nameFood", current.getName());
                                        params.put("amount", String.valueOf(holder.amount.getText()));
                                        params.put("sumPrice",String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(String.valueOf(holder.amount.getText()))));

                                        //params.put(1, )
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue= Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);
                            }
                            else
                            {
                                String urlOrder="http://192.168.1.6/KTCK/updateAmount.php";
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                                    @Override

                                    public void onResponse(String response1) {
                                       // Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();

//
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {

                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("nameTable", nameTable);
                                        params.put("nameFood", current.getName());
                                        String amount1=String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))+ Integer.parseInt(response.trim()));
                                        params.put("amount", amount1);
                                        params.put("sumPrice",String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(amount1)));

                                        //params.put(1, )
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue= Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);
                            }
//
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("nameTable", nameTable);
                            params.put("nameFood", current.getName());


                            //params.put(1, )
                            return params;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                    //Toast.makeText(context, "Position:"+current.getName(), Toast.LENGTH_LONG).show();



                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
