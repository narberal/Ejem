package com.eventosfia.gerardo.UI;

import java.util.List;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.eventosfia.gerardo.ejem.R;
import DAO.EventoDAO;
import DAO.EventoAdapter;
import POJO.Eventos;

public class Main extends ListActivity  {
    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;

    private EventoDAO lEventoDAO; //instância responsável pela persistência dos dados
    List<Eventos> lstEventos;  //lista de contatos cadastrados no BD
    EventoAdapter adapter;   //Adapter responsável por apresentar os contatos na tela

    boolean blnShort = false;
    int Posicao = 0;    //determinar a posição do contato dentro da lista lstContatos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_1);

        lEventoDAO = new EventoDAO(this);
        lEventoDAO.open();

        lstEventos = lEventoDAO.Consultar();

        adapter = new EventoAdapter(this, lstEventos);
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }
    // Este evento será chamado pelo atributo onClick
    // que está definido no botão criado no arquivo main.xml
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                InserirContato();
                break;
        }
    }

    //Rotina executada quando finalizar a Activity ContatoUI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Eventos lAgendaVO = null;

        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK)
            {
                //obtem dados inseridos/alterados na Activity ContatoUI
                lAgendaVO = (Eventos) data.getExtras().getSerializable("agenda");

                //o valor do requestCode foi definido na função startActivityForResult
                if (requestCode == INCLUIR)
                {
                    //verifica se digitou algo no nome do contato
                    if (!lAgendaVO.getNombre().equals(""))
                    {
                        //necessário abrir novamente o BD pois ele foi fechado no método onPause()
                        lEventoDAO.open();

                        //insere o contato no Banco de Dados SQLite
                        lEventoDAO.Inserir(lAgendaVO);

                        //insere o contato na lista de contatos em memória
                        lstEventos.add(lAgendaVO);
                    }
                }else if (requestCode == ALTERAR){
                    lEventoDAO.open();
                    //atualiza o contato no Banco de Dados SQLite
                    lEventoDAO.Alterar(lAgendaVO);

                    //atualiza o contato na lista de contatos em memória
                    lstEventos.set(Posicao, lAgendaVO);
                }

                //método responsável pela atualiza da lista de dados na tela
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    private void InserirContato(){
        try
        {
            //a variável "tipo" tem a função de definir o comportamento da Activity
            //ContatoUI, agora a variável tipo está definida com o valor "0" para
            //informar que será uma inclusão de Contato

            Intent it = new Intent(this, EventoUI.class);
            it.putExtra("tipo", INCLUIR);
            startActivityForResult(it, INCLUIR);//chama a tela e incusão
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        //quando a Activity main receber o foco novamente abre-se novamente a conexão
        lEventoDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //toda vez que o programa peder o foco fecha-se a conexão com o BD
        lEventoDAO.close();
        super.onPause();
    }

    public void toast (String msg)
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try
        {
            //Criação do popup menu com as opções que termos sobre
            //nossos Contatos

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            if (!blnShort)
            {
                Posicao = info.position;
            }
            blnShort = false;

            menu.setHeaderTitle("Selecione:");
            //a origem dos dados do menu está definido no arquivo arrays.xml
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }


    //Este método é disparado quando o usuário clicar em um item do ContextMenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Eventos lAgendaVO = null;
        try
        {
            int menuItemIndex = item.getItemId();

            //Carregar a instância POJO com a posição selecionada na tela
            lAgendaVO = (Eventos) getListAdapter().getItem(Posicao);

            if (menuItemIndex == 0){
                //Carregar a Activity ContatoUI com o registro selecionado na tela

                Intent it = new Intent(this, EventoUI.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("agenda", lAgendaVO);
                startActivityForResult(it, ALTERAR); //chama a tela de alteração
            }else if (menuItemIndex == 1){
                //Excluir do Banco de Dados e da tela o registro selecionado

                lEventoDAO.Excluir(lAgendaVO);
                lstEventos.remove(lAgendaVO);
                adapter.notifyDataSetChanged(); //atualiza a tela
            }
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return true;

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //por padrão o ContextMenu, só é executado através de LongClick, mas
        //nesse caso toda vez que executar um ShortClick, abriremos o menu
        //e também guardaremos qual a posição do itm selecionado

        Posicao = position;
        blnShort = true;
        this.openContextMenu(l);
    }

}