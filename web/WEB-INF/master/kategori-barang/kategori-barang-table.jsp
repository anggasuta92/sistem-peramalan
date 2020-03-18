<%@page import="com.peramalan.model.master.KategoriBarang"%>
<%@page import="java.util.Vector"%>
<%
    Vector datas = (Vector) request.getAttribute("data");
%>
<table id="tbl">
    <tr>
        <th width="50">#</th>
        <th width="200">Kode</th>
        <th>Kategori Barang</th>
        <th width="130">Aksi</th>
    </tr>
    <%
        if(datas!=null && datas.size()>0){
            for(int i = 0; i < datas.size(); i++){
                KategoriBarang data = (KategoriBarang) datas.get(i);
    %>
    <tr>
        <td align="center"><%= (i+1) %></td>
        <td align="center"><%= data.getCode() %></td>
        <td><%= data.getNama() %></td>
        <td align="center">
            <div class="btn-group" role="group">
                <button type="button" class="btn btn-sm btn-primary">Ubah</button>
                <button type="button" class="btn btn-sm btn-warning">Hapus</button>
            </div>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>