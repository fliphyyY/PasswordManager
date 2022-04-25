package filip.ondrusek.uv.passwordmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VaultAdapter extends RecyclerView.Adapter<VaultAdapter.VaultViewHolder> {
    private Context mContext;
    private Cursor vaultCursor;


    public VaultAdapter(Context context, Cursor cursor) {
        mContext = context;
        vaultCursor = cursor;
    }


    public class VaultViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public VaultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textviewVaultList);
        }
    }

    @NonNull
    @Override
    public VaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.vault_recycler_list

                , parent, false);
        return new VaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaultViewHolder holder, int position) {
        if(!vaultCursor.moveToPosition(position)) {
            return;
        }

        String code ="name is: " + vaultCursor.getString(vaultCursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_NAME));
        holder.textView.setText(code);
    }

    @Override
    public int getItemCount() {
        return vaultCursor.getCount();
    }
}
