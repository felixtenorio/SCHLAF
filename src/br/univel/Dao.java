package br.univel;

public class Dao extends SqlGen {
	
	public interface Dao<T, K> {
		public void salvar(T t);
		public T buscar(K k);
		public void atualizar(T t);
		public void excluir(K k);
		public List<T> listarTodos();
		}

}
