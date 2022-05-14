package filip.ondrusek.uv.passwordmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteDatabaseDialog extends AppCompatDialogFragment {

    private TextView textViewDialog;

    public DeleteDatabaseDialog()
    {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.empty_name_dialog, null);
        textViewDialog = view.findViewById(R.id.textViewDialog);
        textViewDialog.setText(getResources().getString(R.string.delete_database_dialog));

        builder.setView(view)
                .setTitle("Warning!")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    getActivity().getApplicationContext().deleteDatabase("VaultDB.db");
                    Intent intent = new Intent(getContext(), CreateVaultActivity.class);
                    startActivity(intent);
                    ((NavigationActivity)getActivity()).showToast(getResources().getString(R.string.delete_database_notice));

                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                });

        return builder.create();
    }
}
