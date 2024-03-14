package com.code.quizappadmin.Adapters;


public class GrideAdapter extends BaseAdapter{

    public int sets=0;
    private String category;
    private String key;

    private GridListener listener;

    public GrideAdapter(int sets, String category, String key, GridListener listener){
        this.sets=sets;
        this.category=category;
        this.key=key;
        this.listener=listener;
        int i;

    }

    @Override
    public int getCount() {
       return sets+1;
    }

    @Override
    public Object getItem(int i){
        return null;
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View veiw, ViewGroup viewGroup){
        
        View view1;

        if(view==null){
            view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sets,viewGroup,false)
        }

        else{
            view1=view;
        }

        if(i==0){
            ((TextView)view1.findViewById(R.id.setName)).setText("+");
        }

        else{
            ((TextView)view1.findViewById(R.id.setName)).setText(String.valueof(i));
        }

        view1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){


                if(i==0){
                    listener.addSets();
                }
                else{
                    Toast.makeText(viewGroup.getContext(),"wait",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view1;
    }

    public interface GridListener{

        public void addSets();
    }
    
}
