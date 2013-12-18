package com.fettmedia.stagebook.domain;

public interface IEntity<E>
{
	public void persist();
	public void remove();
	public E merge();
	
	public Long getId();
	
	/**
	 * Utility method to make a copy of the entity given in the source parameter.
	 * Be aware that there is no default implementation or any use of cloning, serialization
	 * or reflection. This is due to some subtle issues with correct cloning an entity. There
	 * are domain specific demands on how to clone/copy an entity, which must be implemented 
	 * separately on case to case basis.
	 * <p>
	 * NOTE: Please provide documentation to every copy implementation explaining the cloning details.
	 * </p>
	 * @param source - the source entity which is copied
	 * @return A new entity of the same type. The entity's ID is null. IDs for relations are
	 * treated differently on each entity.
	 */
	public E copy(E source);
}
