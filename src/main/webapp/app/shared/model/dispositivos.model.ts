export interface IDispositivos {
  id?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  precioBase?: number;
  moneda?: string;
}

export const defaultValue: Readonly<IDispositivos> = {};
